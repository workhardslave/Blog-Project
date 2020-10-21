package com.cos.blog.service;

import com.cos.blog.domain.Board;
import com.cos.blog.domain.User;
import com.cos.blog.dto.BoardSaveRequestDto;
import com.cos.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 저장 로직
    @Transactional
    public int writeForm(BoardSaveRequestDto dto, User user) {

        System.out.println("BoardService : writeForm 호출");
        dto.giveUser(user);

        return boardRepository.save(dto.toEntity()).getId();
    }

    // 전체 게시물 가져 오는 로직 (페이징)
    @Transactional(readOnly = true)
    public Page<Board> findAllBoards(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

    // 게시글 상세정보 로직
    @Transactional(readOnly = true)
    public Board findABoard(int id) {

        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));
    }

    // 게시글 삭제 로직
    @Transactional
    public void deleteABoard(int id) {
        System.out.println("id = " + id);
        boardRepository.deleteById(id);
    }
    
    // 게시글 수정 로직
    @Transactional
    public int updateABoard(int id, BoardSaveRequestDto dto) {

        // 영속화
        Board board =  boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : " + id));

        // 트랜잭션이 종료되면서, 영속화된 객체의 변화를 확인 -> 더티 체킹 -> update 쿼리 날라감
        board.updateBoard(dto.getTitle(), dto.getContent());

        return id;

    }
}
