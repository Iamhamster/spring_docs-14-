package com.example.springdocs.todo.service;

import com.example.springdocs.member.entity.Member;
import com.example.springdocs.member.repository.MemberRepository;
import com.example.springdocs.todo.dto.*;
import com.example.springdocs.todo.entity.Todo;
import com.example.springdocs.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto save(Long memberId, TodoSaveRequestDto dto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("그 멤버 없음."));
        Todo todo = new Todo(dto.getContent(), member);
        Todo savedTodo = todoRepository.save(todo);
        return new TodoSaveResponseDto(savedTodo.getId(), savedTodo.getContent(), member.getId(), member.getEmail());
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll(){
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtos = new ArrayList<>();
        for (Todo todo: todos) {
            dtos.add(new TodoResponseDto(todo.getId(), todo.getContent()));
        }
        return dtos;
    }

    @Transactional
    public TodoResponseDto findById(Long todoId){
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalStateException("그 todo는 없음"));
        return new TodoResponseDto(todo.getId(), todo.getContent());
    }

    @Transactional
    public TodoUpdateResponseDto update(Long memberId, Long todoId, TodoUpdateRequestDto dto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("그 멤버 없음"));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalStateException("그 todo는 없음"));
        if(!todo.getMember().getId().equals(member.getId())){
            throw new IllegalStateException("그 멤버는 todo 작성자가 아니에요");
        }
        todo.update(dto.getContent());
        return new TodoUpdateResponseDto(todo.getId(), todo.getContent());
    }

    @Transactional
    public void deleteById(Long memberId, Long todoId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("그 멤버 없음"));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalStateException("그 todo 없음"));

        if(!todo.getMember().getId().equals(member.getId())){
            throw new IllegalStateException("그 멤버는 todo 작성자가 아니에요");
        }

        todoRepository.deleteById(todoId);
    }
}
