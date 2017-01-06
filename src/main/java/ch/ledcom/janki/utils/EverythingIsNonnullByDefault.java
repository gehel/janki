package ch.ledcom.janki.utils;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static java.lang.annotation.ElementType.*;

@Nonnull
@TypeQualifierDefault({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EverythingIsNonnullByDefault {
}
