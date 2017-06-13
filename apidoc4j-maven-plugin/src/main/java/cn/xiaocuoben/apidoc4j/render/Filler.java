package cn.xiaocuoben.apidoc4j.render;

import cn.xiaocuoben.apidoc4j.model.ClassComment;
import cn.xiaocuoben.apidoc4j.model.FieldComment;
import cn.xiaocuoben.apidoc4j.model.MethodArgumentComment;
import cn.xiaocuoben.apidoc4j.model.MethodComment;
import org.codehaus.plexus.util.StringUtils;

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
                for (MethodArgumentComment methodArgumentComment : methodComment.getMethodArgumentCommentList()) {
                    StringBuilder argumentJsonBuilder = new StringBuilder();
                    for (FieldComment fieldComment : methodArgumentComment.getFieldCommentList()) {
                        argumentJsonBuilder.append(this.renderFieldList(fieldComment, ""));
                    }
                    methodArgumentComment.setJson(argumentJsonBuilder.toString());
                }

                //返回值
                StringBuilder returnTypeJsonBuilder = new StringBuilder();
                for (FieldComment comment : methodComment.getMethodReturnComment().getFieldCommentList()) {
                    returnTypeJsonBuilder.append(this.renderFieldList(comment, ""));
                }
                methodComment.setMethodReturnTypeCommentJson(returnTypeJsonBuilder.toString());
            }
        }
    }

    public StringBuilder renderFieldList(FieldComment parent, String prefix) {
        StringBuilder jsonBuilder = new StringBuilder();
        if (parent != null && parent.getFieldCommentList() != null) {
            List<FieldComment> parentFieldCommentList = parent.getFieldCommentList();
            for (int i = 0; i < parent.getFieldCommentList().size(); i++) {
                FieldComment fieldComment = parentFieldCommentList.get(i);
                if (!fieldComment.getTypeName().contains("java.lang")) {
                    jsonBuilder
                            .append(prefix + "\t")
                            .append(fieldComment.getName())
                            .append(":");

                    StringBuilder typeNameBuilder = this.renderFieldList(fieldComment, prefix + "\t");
                    jsonBuilder.append(typeNameBuilder.toString());

                    if (i != parentFieldCommentList.size() - 1) {
                        jsonBuilder.append(",");
                    }
                    if (i == parentFieldCommentList.size() - 1) {
                        jsonBuilder
                                .append("\n")
                                .append(prefix)
                                .append("}");
                    }
                } else {
                    if (i == 0) {
                        jsonBuilder.append("{")
                                .append("//")
                                .append(parent.getComment())
                                .append("\n");
                    }
                    jsonBuilder
                            .append(prefix + "\t")
                            .append(fieldComment.getName())
                            .append(":")
                            .append("\"");
                    jsonBuilder.append(fieldComment.getTypeName())
                            .append("\"");
                    if (i != parentFieldCommentList.size() - 1) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append("//").append(fieldComment.getComment()).append("\n");
                    if (i == parentFieldCommentList.size() - 1) {
                        jsonBuilder
                                .append(prefix + "\t")
                                .append("}");
                    }
                }
            }
        }else{
            jsonBuilder.append("{}");
        }
        return jsonBuilder;
    }
}
