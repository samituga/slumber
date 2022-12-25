package io.samituga.demo;

import static io.samituga.slumber.malz.driver.Driver.POSTGRES;

import io.samituga.slumber.malz.database.DataSourceConfig;
import io.samituga.slumber.malz.factory.DataSourceFactory;
import io.samituga.slumber.malz.factory.HikariDataSourceFactory;
import io.samituga.slumber.malz.repository.provider.HikariDataSourceProvider;
import io.samituga.slumber.malz.repository.provider.JooqConnectionProvider;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;


public class Demo {


    public static void main(String[] args) throws IOException {

//        Module module = ModuleLayer.boot()
//              .findModule("io.samituga.demo")
//              // Optional<Module> at this point
//              .orElseThrow();
//        InputStream stream = module.getResourceAsStream("C:\\Users\\sami\\projects\\slumber\\slumber-demo\\src\\main\\resources\\log4j.xml");

//        PropertyConfigurator.configure(stream);
        DOMConfigurator.configure(
              "C:\\Users\\sami\\projects\\slumber\\slumber-demo\\src\\main\\resources\\log4j.xml");
        DOMConfigurator.configure(
              "C:\\Users\\sami\\projects\\slumber\\slumber-demo\\src\\main\\resources\\log4j.xml");

        var jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        var user = "postgres";
        var password = "postgres";

        var dbConfig = DataSourceConfig.builder()
              .driverClass(POSTGRES)
              .jdbcUrl(jdbcUrl)
              .user(user)
              .password(password)
              .build();

        DataSourceFactory dataSourceFactory = new HikariDataSourceFactory();

        final var a = new JooqConnectionProvider(
              new HikariDataSourceProvider(dataSourceFactory, dbConfig));
        final var bookRepository = new BookRepository(a);

        final var result1 = bookRepository.find(1).get();
        final var result2 = bookRepository.find(2).get();

        final var updated1 = BookEntity.builder().copy(result1).title("New title5").build();
        final var updated2 = BookEntity.builder().copy(result2).title("New title6").build();

        System.out.println("\nupdate start\n");

        bookRepository.updateAll(List.of(updated1, updated2));

        System.out.println("\nupdate end\n");

        final var updatedResult1 = bookRepository.find(1).get();
        final var updatedResult2 = bookRepository.find(2).get();

        System.out.println(updatedResult1);


    }
}
