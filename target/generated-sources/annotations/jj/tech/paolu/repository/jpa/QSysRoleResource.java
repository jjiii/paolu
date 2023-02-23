package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSysRoleResource is a Querydsl query type for SysRoleResource
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysRoleResource extends EntityPathBase<SysRoleResource> {

    private static final long serialVersionUID = -955731317L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSysRoleResource sysRoleResource = new QSysRoleResource("sysRoleResource");

    public final QSysRoleResourceId id;

    public QSysRoleResource(String variable) {
        this(SysRoleResource.class, forVariable(variable), INITS);
    }

    public QSysRoleResource(Path<? extends SysRoleResource> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSysRoleResource(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSysRoleResource(PathMetadata metadata, PathInits inits) {
        this(SysRoleResource.class, metadata, inits);
    }

    public QSysRoleResource(Class<? extends SysRoleResource> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSysRoleResourceId(forProperty("id")) : null;
    }

}

