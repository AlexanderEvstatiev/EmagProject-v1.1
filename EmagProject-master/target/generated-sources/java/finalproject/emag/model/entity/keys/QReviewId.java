package finalproject.emag.model.entity.keys;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewId is a Querydsl query type for ReviewId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QReviewId extends BeanPath<ReviewId> {

    private static final long serialVersionUID = 730974102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewId reviewId = new QReviewId("reviewId");

    public final finalproject.emag.model.entity.QProduct product;

    public final finalproject.emag.model.entity.QUser user;

    public QReviewId(String variable) {
        this(ReviewId.class, forVariable(variable), INITS);
    }

    public QReviewId(Path<? extends ReviewId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewId(PathMetadata metadata, PathInits inits) {
        this(ReviewId.class, metadata, inits);
    }

    public QReviewId(Class<? extends ReviewId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new finalproject.emag.model.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new finalproject.emag.model.entity.QUser(forProperty("user")) : null;
    }

}

