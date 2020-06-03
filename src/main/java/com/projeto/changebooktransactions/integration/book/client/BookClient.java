package com.projeto.changebooktransactions.integration.book.client;

import com.projeto.changebooktransactions.integration.book.response.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "book", url = "${book.url}")
public interface BookClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    Book getBookById(@PathVariable("bookId") String bookId,
                     @RequestHeader("Authorization") String token);

}
