package cloud.dispatcher.base.framework.utils;

import lombok.ToString;

public final class TupleUtil {

    private TupleUtil() {}

    public static <A, B, C, D> Tuple4<A, B, C, D> tuple4(A e1, B e2, C e3, D e4) {
        return new Tuple4<>(e1, e2, e3, e4);
    }

    public static <A, B> Tuple2<A, B> tuple2(A e1, B e2) {
        return new Tuple2<>(e1, e2);
    }

    public static <A, B, C> Tuple3<A, B, C> tuple3(A e1, B e2, C e3) {
        return new Tuple3<>(e1, e2, e3);
    }

    @ToString
    public static class Tuple4<A, B, C, D> {

        public final A e1;

        public final B e2;

        public final C e3;

        public final D e4;

        public Tuple4(A e1, B e2, C e3, D e4) {
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
            this.e4 = e4;
        }
    }

    @ToString
    public static class Tuple2<A, B> {

        public final A e1;

        public final B e2;

        public Tuple2(A e1, B e2) {
            this.e1 = e1;
            this.e2 = e2;
        }
    }

    @ToString
    public static class Tuple3<A, B, C> {

        public final A e1;

        public final B e2;

        public final C e3;

        public Tuple3(A e1, B e2, C e3) {
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
        }
    }
}
