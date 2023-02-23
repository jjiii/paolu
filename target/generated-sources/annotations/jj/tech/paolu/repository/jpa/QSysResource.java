package jj.tech.paolu.repository.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysResource is a Querydsl query type for SysResource
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSysResource extends EntityPathBase<SysResource> {

    private static final long serialVersionUID = -1120948363L;

    public static final QSysResource sysResource = new QSysResource("sysResource");

    public final StringPath description = createString("description");

    public final StringPath icon = createString("icon");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Byte> ishide = createNumber("ishide", Byte.class);

    public final NumberPath<Short> level = createNumber("level", Short.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath types = createString("types");

    public final StringPath url = createString("url");

    public QSysResource(String variable) {
        super(SysResource.class, forVariable(variable));
    }

    public QSysResource(Path<? extends SysResource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysResource(PathMetadata metadata) {
        super(SysResource.class, metadata);
    }

}

