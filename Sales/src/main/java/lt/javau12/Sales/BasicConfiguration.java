package lt.javau12.Sales;

import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.repositories.GoodsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfiguration {

//    @Bean
//    public CommandLineRunner seedGoods(GoodsRepository goodsRepository){
//        return (x) -> {
//             if (goodsRepository.count() <= 5){
//                 goodsRepository.save( new Goods(null, "Mouse", "Logitech Wireless Mouse", 58.45, ));
//             }
//        };
//    }
}
