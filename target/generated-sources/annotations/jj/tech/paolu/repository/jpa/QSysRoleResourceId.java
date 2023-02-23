package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleResourceId is a Querydsl query type for SysRoleResourceId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSysRoleResourceId extends BeanPath<SysRoleResourceId> {

    private static final long serialVersionUID = 665208070L;

    public static final QSysRoleResourceId sysRoleResourceId = new QSysRoleResourceId("sysRoleResourceId");

    public final NumberPath<Long> resId = createNumber("resId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QSysRoleResourceId(String variable) {
        super(SysRoleResourceId.class, forVariable(variable));
    }

    public QSysRoleResourceId(Path<? extends SysRoleResourceId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleResourceId(PathMetadata metadata) {
        super(SysRoleResourceId.class, metadata);
    }

}

