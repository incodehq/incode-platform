package org.isisaddons.module.command.fixture.dom;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

@Mixin
public class SomeCommandAnnotatedObject_throwsUnrecognizedException {

    private final SomeCommandAnnotatedObject someCommandAnnotatedObject;

    public SomeCommandAnnotatedObject_throwsUnrecognizedException(SomeCommandAnnotatedObject someCommandAnnotatedObject) {
        this.someCommandAnnotatedObject = someCommandAnnotatedObject;
    }

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
    public SomeCommandAnnotatedObject $$() {
        throw new MyRuntimeException("This failed...");
    }

    public static class MyRuntimeException extends RuntimeException {
        public MyRuntimeException() {
        }

        public MyRuntimeException(final String message) {
            super(message);
        }

        public MyRuntimeException(final String message, final Throwable cause) {
            super(message, cause);
        }

        public MyRuntimeException(final Throwable cause) {
            super(cause);
        }

        public MyRuntimeException(
                final String message,
                final Throwable cause,
                final boolean enableSuppression,
                final boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

}
