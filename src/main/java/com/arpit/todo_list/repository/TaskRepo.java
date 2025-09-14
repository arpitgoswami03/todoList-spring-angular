package com.arpit.todo_list.repository;

import com.arpit.todo_list.model.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Tasks, Long> {

    @Query("SELECT t FROM tasks t WHERE t.user.id = :userId")
    List<Tasks> findAllByUserId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM tasks t WHERE t.id = :taskId AND t.user.id = :userId")
    void deleteByIdAndUserId(Long taskId, Long userId);

    @Query("SELECT t FROM tasks t WHERE t.id = :taskId AND t.user.id = :userId")
    Tasks findTaskById(Long userId, Long taskId);
}
