package finalproject.emag.model.pojo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1664979046L;

    public static final QUser user = new QUser("user");

    public final BooleanPath admin = createBoolean("admin");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final BooleanPath subscribed = createBoolean("subscribed");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

