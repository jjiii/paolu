package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysAdmin is a Querydsl query type for SysAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysAdmin extends EntityPathBase<SysAdmin> {

    private static final long serialVersionUID = 350562632L;

    public static final QSysAdmin sysAdmin = new QSysAdmin("sysAdmin");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Byte> isLocked = createNumber("isLocked", Byte.class);

    public final StringPath password = createString("password");

    public final StringPath realname = createString("realname");

    public final StringPath username = createString("username");

    public QSysAdmin(String variable) {
        super(SysAdmin.class, forVariable(variable));
    }

    public QSysAdmin(Path<? extends SysAdmin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysAdmin(PathMetadata metadata) {
        super(SysAdmin.class, metadata);
    }

}

