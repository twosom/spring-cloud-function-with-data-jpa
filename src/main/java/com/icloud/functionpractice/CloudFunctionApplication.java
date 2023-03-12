package com.icloud.functionpractice;

import com.icloud.functionpractice.entity.Memo;
import com.icloud.functionpractice.entity.MemoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@SpringBootApplication
public class CloudFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudFunctionApplication.class, args);
    }

    @Bean
    Function<String, String> uppercase() {
        return String::toUpperCase;
    }

    @Bean
    Function<Long, Memo> getMemo(MemoRepository memoRepository) {
        return id -> memoRepository.findById(id).get();
    }


    @Component
    static class MemoInsertRunner implements ApplicationRunner {

        private final MemoRepository memoRepository;

        MemoInsertRunner(MemoRepository memoRepository) {
            this.memoRepository = memoRepository;
        }


        @Override
        public void run(ApplicationArguments args) {
            Memo memo = new Memo("hello twosom");
            memoRepository.save(memo);
            System.out.println(memo.getId());
            memo = new Memo("hello hope");
            memoRepository.save(memo);
            System.out.println(memo.getId());
        }
    }

}
