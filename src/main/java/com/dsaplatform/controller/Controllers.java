package com.dsaplatform.controller;

import com.dsaplatform.security.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

// ── TopicController ──────────────────────────────────────
@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "*")
class TopicController {

    @Autowired Jwtutil jwtUtil;

    // GET /api/topics/{courseId}  — get all topics for a course
    @GetMapping("/{courseId}")
    public ResponseEntity<?> getTopics(
            @PathVariable String courseId,
            @RequestHeader("Authorization") String authHeader) {
        // Extract user from JWT, fetch topics from DB
        // (DB layer omitted here — connect to your TopicRepository)
        return ResponseEntity.ok(Map.of("message", "Return topics for course " + courseId));
    }

    // POST /api/topics  — add new topic
    @PostMapping
    public ResponseEntity<?> addTopic(
            @RequestBody Map<String, Object> body,
            @RequestHeader("Authorization") String authHeader) {
        /*
         * Expected body:
         * {
         *   "courseId": "...",
         *   "title": "Binary Search",
         *   "phase": "P1",
         *   "num": 5,
         *   "lecUrl": "https://...",
         *   "concepts": ["concept1", "concept2"]
         * }
         */
        return ResponseEntity.ok(Map.of("message", "Topic added", "body", body));
    }

    // DELETE /api/topics/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(Map.of("message", "Topic " + id + " deleted"));
    }
}

// ── ProblemController ──────────────────────────────────────
@RestController
@RequestMapping("/api/problems")
@CrossOrigin(origins = "*")
class ProblemController {

    @Autowired Jwtutil jwtUtil;

    // POST /api/problems  — add problem to a topic
    @PostMapping
    public ResponseEntity<?> addProblem(
            @RequestBody Map<String, Object> body,
            @RequestHeader("Authorization") String authHeader) {
        /*
         * Expected body:
         * {
         *   "topicId": 1,
         *   "name": "Valid Parentheses",
         *   "lcNumber": 20,
         *   "difficulty": "Easy",
         *   "url": "https://leetcode.com/..."
         * }
         */
        return ResponseEntity.ok(Map.of("message", "Problem added", "body", body));
    }

    // PUT /api/problems/{id}/solve  — mark problem as solved + schedule revision
    @PutMapping("/{id}/solve")
    public ResponseEntity<?> solveProblem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        int[] INTERVALS = {1, 3, 5, 10, 15, 30, 60, 120, 150, 200, 250, 300};
        List<Map<String,Object>> schedule = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int days : INTERVALS) {
            Map<String,Object> entry = new HashMap<>();
            entry.put("problemId", id);
            entry.put("dueDate", today.plusDays(days).toString());
            entry.put("intervalDay", days);
            entry.put("completed", false);
            schedule.add(entry);
            // Save each entry to RevisionSchedule table via repository
        }

        return ResponseEntity.ok(Map.of(
                "message", "Problem solved! Revision scheduled.",
                "schedule", schedule
        ));
    }

    // DELETE /api/problems/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProblem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(Map.of("message", "Problem " + id + " deleted"));
    }
}

// ── ProgressController ──────────────────────────────────────
@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
class ProgressController {

    // PUT /api/progress  — update topic progress checkboxes
    @PutMapping
    public ResponseEntity<?> updateProgress(
            @RequestBody Map<String, Object> body,
            @RequestHeader("Authorization") String authHeader) {
        /*
         * body: { "topicId": 1, "field": "lec", "value": true }
         * field options: lec, prob, prac, day
         */
        return ResponseEntity.ok(Map.of("message", "Progress updated", "body", body));
    }

    // GET /api/progress/stats/{courseId}  — get course stats
    @GetMapping("/stats/{courseId}")
    public ResponseEntity<?> getStats(
            @PathVariable String courseId,
            @RequestHeader("Authorization") String authHeader) {
        // Query DB for days done, lectures, problems, practice counts
        return ResponseEntity.ok(Map.of(
                "daysDone", 0,
                "lectures", 0,
                "problems", 0,
                "practice", 0,
                "totalTasks", 0,
                "doneTasks", 0
        ));
    }
}

// ── NoteController ──────────────────────────────────────
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
class NoteController {

    // POST /api/notes  — save or update a note
    @PostMapping
    public ResponseEntity<?> saveNote(
            @RequestBody Map<String, Object> body,
            @RequestHeader("Authorization") String authHeader) {
        /*
         * body: { "problemId": 1, "content": "My notes..." }
         */
        return ResponseEntity.ok(Map.of("message", "Note saved"));
    }

    // GET /api/notes/{problemId}
    @GetMapping("/{problemId}")
    public ResponseEntity<?> getNote(
            @PathVariable Long problemId,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(Map.of("content", ""));
    }

    // DELETE /api/notes/{problemId}
    @DeleteMapping("/{problemId}")
    public ResponseEntity<?> deleteNote(
            @PathVariable Long problemId,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(Map.of("message", "Note deleted"));
    }
}

// ── RevisionController ──────────────────────────────────────
@RestController
@RequestMapping("/api/revision")
@CrossOrigin(origins = "*")
class RevisionController {

    // GET /api/revision/due  — get today's due + overdue revisions
    @GetMapping("/due")
    public ResponseEntity<?> getDue(@RequestHeader("Authorization") String authHeader) {
        // Query RevisionSchedule where dueDate <= today AND completed = false
        return ResponseEntity.ok(Map.of("due", List.of(), "upcoming", List.of()));
    }

    // PUT /api/revision/{id}/done  — mark a revision as done
    @PutMapping("/{id}/done")
    public ResponseEntity<?> markDone(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(Map.of("message", "Revision marked done"));
    }

    // GET /api/revision/calendar/{year}/{month}  — get revision dates for calendar
    @GetMapping("/calendar/{year}/{month}")
    public ResponseEntity<?> getCalendar(
            @PathVariable int year,
            @PathVariable int month,
            @RequestHeader("Authorization") String authHeader) {
        // Return list of days that have revisions in given month
        return ResponseEntity.ok(Map.of("revisionDays", List.of()));
    }
}
