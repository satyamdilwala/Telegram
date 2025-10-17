package tglive.fqrs.app;

public interface GenericProvider<F, T> {
    T provide(F obj);
}
