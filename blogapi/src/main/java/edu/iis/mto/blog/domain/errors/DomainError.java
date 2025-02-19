package edu.iis.mto.blog.domain.errors;

public class DomainError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String USER_NOT_FOUND = "unknown user";
    public static final String POST_NOT_FOUND = "unknown post";
    public static final String SELF_LIKE = "cannot like own post";
    public static final String USER_NOT_CONFIRMED = "user must be confirmed to do this";
    public static final String CANT_LIKE_TWICE = "cannot like same post twice";
    public static final String USER_REMOVED = "removed user";

    public DomainError(String msg) {
        super(msg);
    }

}
