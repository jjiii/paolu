/*
 * This file is generated by jOOQ.
 */
package jj.tech.paolu.repository.jooq;


import java.util.Arrays;
import java.util.List;

import jj.tech.paolu.repository.jooq.tables.SysAdmin;
import jj.tech.paolu.repository.jooq.tables.SysResource;
import jj.tech.paolu.repository.jooq.tables.SysRole;
import jj.tech.paolu.repository.jooq.tables.SysRoleAdmin;
import jj.tech.paolu.repository.jooq.tables.SysRoleResource;
import jj.tech.paolu.repository.jooq.tables.UserInfo;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.SYS_ADMIN</code>.
     */
    public final SysAdmin SYS_ADMIN = SysAdmin.SYS_ADMIN;

    /**
     * The table <code>PUBLIC.SYS_RESOURCE</code>.
     */
    public final SysResource SYS_RESOURCE = SysResource.SYS_RESOURCE;

    /**
     * The table <code>PUBLIC.SYS_ROLE</code>.
     */
    public final SysRole SYS_ROLE = SysRole.SYS_ROLE;

    /**
     * The table <code>PUBLIC.SYS_ROLE_ADMIN</code>.
     */
    public final SysRoleAdmin SYS_ROLE_ADMIN = SysRoleAdmin.SYS_ROLE_ADMIN;

    /**
     * The table <code>PUBLIC.SYS_ROLE_RESOURCE</code>.
     */
    public final SysRoleResource SYS_ROLE_RESOURCE = SysRoleResource.SYS_ROLE_RESOURCE;

    /**
     * The table <code>PUBLIC.USER_INFO</code>.
     */
    public final UserInfo USER_INFO = UserInfo.USER_INFO;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            SysAdmin.SYS_ADMIN,
            SysResource.SYS_RESOURCE,
            SysRole.SYS_ROLE,
            SysRoleAdmin.SYS_ROLE_ADMIN,
            SysRoleResource.SYS_ROLE_RESOURCE,
            UserInfo.USER_INFO
        );
    }
}
