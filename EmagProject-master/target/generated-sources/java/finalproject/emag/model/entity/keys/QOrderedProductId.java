package finalproject.emag.model.entity.keys;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderedProductId is a Querydsl query type for OrderedProductId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QOrderedProductId extends BeanPath<OrderedProductId> {

    private static final long serialVersionUID = 1785934464L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderedProductId orderedProductId = new QOrderedProductId("orderedProductId");

    public final finalproject.emag.model.entity.QOrder order;

    public final finalproject.emag.model.entity.QProduct product;

    public QOrderedProductId(String variable) {
        this(OrderedProductId.class, forVariable(variable), INITS);
    }

    public QOrderedProductId(Path<? extends OrderedProductId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderedProductId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderedProductId(PathMetadata metadata, PathInits inits) {
        this(OrderedProductId.class, metadata, inits);
    }

    public QOrderedProductId(Class<? extends OrderedProductId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new finalproject.emag.model.entity.QOrder(forProperty("order"), inits.get("order")) : null;
        this.product = inits.isInitialized("product") ? new finalproject.emag.model.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

