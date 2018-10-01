package com.huawei.master.app.restful;

import com.huawei.master.app.dao.BookRepository;
import com.huawei.master.app.domain.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Api(value = "书籍管理", description = "书籍管理")
@RequestMapping(value = "/rest/book")
public class BookRestful {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    @ApiOperation(value = "创建书籍", notes = "根据book对象创建书籍")
    @ApiImplicitParam(name = "book", value = "书籍详细实体book", required = true, dataType = "Book")
    public String postBook(@RequestBody Book book) {
        bookRepository.save(book);
        return "success";
    }

    @GetMapping
    @ApiOperation(value = "获取书籍列表", notes = "获取所有书籍列表")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @ApiOperation(value = "获取书籍详细信息", notes = "根据url的id来获取书籍详细信息")
    @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String id) {
        Optional<Book> op = bookRepository.findById(id);
        return op.isPresent() ? op.get() : null;
    }


    @ApiOperation(value = "更新书籍详细信息", notes = "根据id来指定更新对象，并根据传过来的信息来更新书籍详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "book", value = "书籍详细实体book", required = true, dataType = "Book")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putBook(@PathVariable String id, @RequestBody Book book) {
        book.setId(id);
        bookRepository.save(book);
        return "success";
    }

    @ApiOperation(value = "删除书籍", notes = "根据id来指定删除书籍")
    @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id);
        return "success";
    }
    

}
