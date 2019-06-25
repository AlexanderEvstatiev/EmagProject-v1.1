package finalproject.emag.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStat is a Querydsl query type for Stat
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStat extends EntityPathBase<Stat> {

    private static final long serialVersionUID = -1350075227L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStat stat = new QStat("stat");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QProduct product;

    public final StringPath unit = createString("unit");

    public final StringPath value = createString("value");

    public QStat(String variable) {
        this(Stat.class, forVariable(variable), INITS);
    }

    public QStat(Path<? extends Stat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStat(PathMetadata metadata, PathInits inits) {
        this(Stat.class, metadata, inits);
    }

    public QStat(Class<? extends Stat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

