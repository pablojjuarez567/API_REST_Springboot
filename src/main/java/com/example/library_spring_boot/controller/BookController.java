package com.example.library_spring_boot.controller;

import com.example.library_spring_boot.model.BookModel;
import com.example.library_spring_boot.model.BookRepository;
import com.example.library_spring_boot.model.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public List<BookResponse> getBooks() {
        List<BookModel> books = bookRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();

        for (BookModel bookModel : books) {
            BookResponse bookResponse = new BookResponse(bookModel.getId(), bookModel.getTitle());
            bookResponses.add(bookResponse);
        }

        return bookResponses;
    }

    @GetMapping("/{id}")
    public BookModel getBookById(@PathVariable Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @GetMapping("/category/{category}")
    public List<BookModel> getBooksByCategoria(@PathVariable String category) {
        return bookRepository.findByCategory(category);
    }

    @GetMapping("/author/{author}")
    public List<BookModel> getBooksByAutor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    @PostMapping("")
    public BookModel addBook(@RequestBody BookModel book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@RequestBody BookModel book, @PathVariable Long id) {
        Optional<BookModel> bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        book.setId(id);
        bookRepository.save(book);

        return ResponseEntity.ok(book);
    }


    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
