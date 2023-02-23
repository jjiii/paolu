package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRole is a Querydsl query type for SysRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysRole extends EntityPathBase<SysRole> {

    private static final long serialVersionUID = 1674393437L;

    public static final QSysRole sysRole = new QSysRole("sysRole");

    public final StringPath department = createString("department");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentid = createNumber("parentid", Long.class);

    public final StringPath state = createString("state");

    public QSysRole(String variable) {
        super(SysRole.class, forVariable(variable));
    }

    public QSysRole(Path<? extends SysRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRole(PathMetadata metadata) {
        super(SysRole.class, metadata);
    }

}

