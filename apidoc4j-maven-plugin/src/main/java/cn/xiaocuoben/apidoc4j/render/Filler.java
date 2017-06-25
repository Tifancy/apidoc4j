package cn.xiaocuoben.apidoc4j.render;

import cn.xiaocuoben.apidoc4j.model.ClassComment;
import cn.xiaocuoben.apidoc4j.model.FieldComment;
import cn.xiaocuoben.apidoc4j.model.MethodComment;

import java.util.List;

/**
 * @author Frank
 * @version 2017/6/13
 */
public class Filler {

    public void fillClass(List<ClassComment> classCommentList) {
        for (ClassComment classComment : classCommentList) {
            for (MethodComment methodComment : classComment.getMethodCommentList()) {
                //参数
                FieldComment argumentComment = methodComment.getMethodArgumentComment();

                StringBuilder argumentJsonBuilder = new StringBuilder();
                argumentJsonBuilder.append("[").append("\n").append("\t");
                for (FieldComment fieldComment : argumentComment.getFieldCommentList()) {
                    this.renderFieldList(fieldComment, "\t", argumentJsonBuilder);
                }
                argumentJsonBuilder.append("]");
                methodComment.setMethodArgumentCommentJson(argumentJsonBuilder.toString());

                //返回值
                StringBuilder returnTypeJsonBuilder = new StringBuilder();
                this.renderFieldList(methodComment.getMethodReturnComment(), "\t", returnTypeJsonBuilder);
                methodComment.setMethodReturnTypeCommentJson(returnTypeJsonBuilder.toString());
            }
        }
    }

    public StringBuilder renderFieldList(FieldComment parent, String prefix, StringBuilder jsonBuilder) {
        if (parent == null || parent.getFieldCommentList() == null) {
            return jsonBuilder.append("{}").append("\n");
        }
        List<FieldComment> parentFieldCommentList = parent.getFieldCommentList();
        //head
        jsonBuilder.append("{")
                .append("//")
                .append(parent.getComment() == null ? "" : parent.getComment())
                .append("\n");
        for (int i = 0; i < parent.getFieldCommentList().size(); i++) {
            FieldComment fieldComment = parentFieldCommentList.get(i);
            if (!fieldComment.getTypeName().contains("java.lang")) {
                jsonBuilder.append(prefix + "\t").append("\"").append(fieldComment.getName())
                        .append("\"").append(":");
                this.renderFieldList(fieldComment, prefix + "\t", jsonBuilder);
            } else {
                jsonBuilder
                        .append(prefix + "\t")
                        .append("\"")
                        .append(fieldComment.getName())
                        .append("\"")
                        .append(":")
                        .append("\"");
                jsonBuilder.append(fieldComment.getTypeName())
                        .append("\"");
                jsonBuilder.append(",");
                jsonBuilder.append("//").append(fieldComment.getComment()).append("\n");
            }
        }
        //foot
        jsonBuilder
                .append(prefix)
                .append("}")
                .append("\n");
        return jsonBuilder;
    }
}
