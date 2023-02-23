package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -1845197857L;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final StringPath headImg = createString("headImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final StringPath realName = createString("realName");

    public final StringPath userName = createString("userName");

    public QUserInfo(String variable) {
        super(UserInfo.class, forVariable(variable));
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserInfo(PathMetadata metadata) {
        super(UserInfo.class, metadata);
    }

}

