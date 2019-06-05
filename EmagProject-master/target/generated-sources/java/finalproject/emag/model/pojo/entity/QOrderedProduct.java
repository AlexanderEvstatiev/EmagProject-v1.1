package finalproject.emag.model.pojo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderedProduct is a Querydsl query type for OrderedProduct
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderedProduct extends EntityPathBase<OrderedProduct> {

    private static final long serialVersionUID = -1911265635L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderedProduct orderedProduct = new QOrderedProduct("orderedProduct");

    public final finalproject.emag.model.pojo.composite.keys.QOrderedProductId id;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrderedProduct(String variable) {
        this(OrderedProduct.class, forVariable(variable), INITS);
    }

    public QOrderedProduct(Path<? extends OrderedProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderedProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderedProduct(PathMetadata metadata, PathInits inits) {
        this(OrderedProduct.class, metadata, inits);
    }

    public QOrderedProduct(Class<? extends OrderedProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new finalproject.emag.model.pojo.composite.keys.QOrderedProductId(forProperty("id"), inits.get("id")) : null;
    }

}

