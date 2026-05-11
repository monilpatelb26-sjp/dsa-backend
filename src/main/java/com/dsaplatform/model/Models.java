package com.dsaplatform.model;

import jakarta.persistence.*;

// ── Problem ──────────────────────────
@Entity
@Table(name = "problems")
class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer lcNumber;
    private String difficulty; // Easy, Medium, Hard
    private String url;

    @ManyToOne @JoinColumn(name = "topic_id")
    private Topic topic;

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public String getName(){return name;}public void setName(String n){this.name=n;}
    public Integer getLcNumber(){return lcNumber;}public void setLcNumber(Integer n){this.lcNumber=n;}
    public String getDifficulty(){return difficulty;}public void setDifficulty(String d){this.difficulty=d;}
    public String getUrl(){return url;}public void setUrl(String u){this.url=u;}
    public Topic getTopic(){return topic;}public void setTopic(Topic t){this.topic=t;}
}

// ── Course ───────────────────────────
@Entity
@Table(name = "courses")
class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String badge; // ongoing, new, coming

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public String getName(){return name;}public void setName(String n){this.name=n;}
    public String getDescription(){return description;}public void setDescription(String d){this.description=d;}
    public String getIcon(){return icon;}public void setIcon(String i){this.icon=i;}
    public String getBadge(){return badge;}public void setBadge(String b){this.badge=b;}
    public User getUser(){return user;}public void setUser(User u){this.user=u;}
}

// ── TopicProgress ─────────────────────
@Entity
@Table(name = "topic_progress")
class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")  private User user;
    @ManyToOne @JoinColumn(name = "topic_id") private Topic topic;

    private Boolean lecDone   = false;
    private Boolean probDone  = false;
    private Boolean pracDone  = false;
    private Boolean dayDone   = false;

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public User getUser(){return user;}public void setUser(User u){this.user=u;}
    public Topic getTopic(){return topic;}public void setTopic(Topic t){this.topic=t;}
    public Boolean getLecDone(){return lecDone;}public void setLecDone(Boolean b){this.lecDone=b;}
    public Boolean getProbDone(){return probDone;}public void setProbDone(Boolean b){this.probDone=b;}
    public Boolean getPracDone(){return pracDone;}public void setPracDone(Boolean b){this.pracDone=b;}
    public Boolean getDayDone(){return dayDone;}public void setDayDone(Boolean b){this.dayDone=b;}
}

// ── ProblemSolved ─────────────────────
@Entity
@Table(name = "problem_solved")
class ProblemSolved {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")    private User user;
    @ManyToOne @JoinColumn(name = "problem_id") private Problem problem;
    private Boolean solved = false;

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public User getUser(){return user;}public void setUser(User u){this.user=u;}
    public Problem getProblem(){return problem;}public void setProblem(Problem p){this.problem=p;}
    public Boolean getSolved(){return solved;}public void setSolved(Boolean b){this.solved=b;}
}

// ── Note ─────────────────────────────
@Entity
@Table(name = "notes")
class Note {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")    private User user;
    @ManyToOne @JoinColumn(name = "problem_id") private Problem problem;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public User getUser(){return user;}public void setUser(User u){this.user=u;}
    public Problem getProblem(){return problem;}public void setProblem(Problem p){this.problem=p;}
    public String getContent(){return content;}public void setContent(String c){this.content=c;}
}

// ── RevisionSchedule ─────────────────
@Entity
@Table(name = "revision_schedule")
class RevisionSchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")    private User user;
    @ManyToOne @JoinColumn(name = "problem_id") private Problem problem;

    private java.time.LocalDate dueDate;
    private Boolean completed = false;
    private Integer intervalDay; // 1,3,5,10,15...

    public Long getId(){return id;}public void setId(Long id){this.id=id;}
    public User getUser(){return user;}public void setUser(User u){this.user=u;}
    public Problem getProblem(){return problem;}public void setProblem(Problem p){this.problem=p;}
    public java.time.LocalDate getDueDate(){return dueDate;}public void setDueDate(java.time.LocalDate d){this.dueDate=d;}
    public Boolean getCompleted(){return completed;}public void setCompleted(Boolean b){this.completed=b;}
    public Integer getIntervalDay(){return intervalDay;}public void setIntervalDay(Integer i){this.intervalDay=i;}
}
