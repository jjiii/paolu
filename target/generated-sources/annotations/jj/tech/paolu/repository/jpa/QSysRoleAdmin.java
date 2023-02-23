package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleAdmin is a Querydsl query type for SysRoleAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysRoleAdmin extends EntityPathBase<SysRoleAdmin> {

    private static final long serialVersionUID = 1901260274L;

    public static final QSysRoleAdmin sysRoleAdmin = new QSysRoleAdmin("sysRoleAdmin");

    public final NumberPath<Long> adminId = createNumber("adminId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QSysRoleAdmin(String variable) {
        super(SysRoleAdmin.class, forVariable(variable));
    }

    public QSysRoleAdmin(Path<? extends SysRoleAdmin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleAdmin(PathMetadata metadata) {
        super(SysRoleAdmin.class, metadata);
    }

}

