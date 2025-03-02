package config;

import repository.DatabaseConnection;
import com.google.inject.AbstractModule;
import repository.ProductRepository;
import repository.UserRepository;
import service.ProductService;
import service.UserService;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseConnection.class).in(com.google.inject.Singleton.class);
        bind(UserRepository.class).in(com.google.inject.Singleton.class);
        bind(UserService.class).in(com.google.inject.Singleton.class);
        bind(ProductRepository.class).in(com.google.inject.Singleton.class);
        bind(ProductService.class).in(com.google.inject.Singleton.class);
    }
}
