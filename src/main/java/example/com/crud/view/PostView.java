package example.com.crud.view;

import example.com.crud.controller.PostController;
import example.com.crud.model.Label;
import example.com.crud.model.Post;
import example.com.crud.model.PostStatus;
import example.com.crud.model.Writer;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);
    private final PostController postController;


    public PostView() {
        this.postController = new PostController();
    }

    public void show() {
        int select;

        while (true) {
            System.out.println("=========================");
            System.out.println("Меню управления статьями:");
            System.out.println("=========================");
            System.out.println();
            System.out.println("1. Посмотреть список всех статей");
            System.out.println("2. Добавить новую статью");
            System.out.println("3. Найти статью по id");
            System.out.println("4. Изменить содержимое или статус статьи");
            System.out.println("5. Удалить статью");
            System.out.println("6. Выход");
            System.out.println();
            System.out.println("====================");
            System.out.println("Выберите пункт меню:");
            System.out.println("====================");
            select = scanner.nextInt();
            System.out.println("\n");

            switch (select) {
                case 1:
                    eventShowAllPosts();
                    break;

                case 2:
                    eventCreateNewPost();
                    break;

                case 3:
                    eventGetPostById();
                    break;
                case 4:
                    eventUpdatePostById();
                    break;
                case 5:
                    eventDeletePostById();
                    break;
                case 6:
                    System.out.println("********************Работа приложения завершена!********************");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неправильно выбран пункт! Пожалуйста, повторите ввод");
                    break;
            }
        }

    }

    public void eventShowAllPosts() {
        System.out.println("===========================");
        System.out.println("Список существующих статей:");
        System.out.println("===========================");

        for (Post post : postController.onShowAll()) {
            System.out.println(post.toStringAllData());
        }

        System.out.println();
        System.out.println("=======================================================================");
    }

    public void eventCreateNewPost() {
        String content = "";
        Long labelId;
        PostStatus postStatus = null;
        int statusNum;
        Long writerId;

        System.out.println("========================");
        System.out.println("Добавление новой статьи:");
        System.out.println("========================");

        System.out.println("Введите название новой статьи: ");
        System.out.println();
        scanner.nextLine();
        content = scanner.nextLine();

        System.out.println();
        System.out.println("Укажите автора новой статьи из списка существующих: ");
        List<Writer> writers = postController.getAllWriters();
        System.out.println();

        for (Writer allWriter : writers) {
            System.out.println(allWriter);
        }
        System.out.println();
        System.out.println("Введите id автора статьи: ");

        writerId = scanner.nextLong();

        System.out.println();
        System.out.println("Выберите статус статьи из списка, введя номер статуса: ");
        System.out.println("1. ACTIVE " + "\n" +
                           "2. UNDER_REVIEW " + "\n" +
                           "3. DELETED " + "\n");

        statusNum = scanner.nextInt();

        switch (statusNum) {
            case 1:
                postStatus = PostStatus.ACTIVE;
                break;
            case 2:
                postStatus = PostStatus.UNDER_REVIEW;
                break;
            case 3:
                postStatus = PostStatus.DELETED;
                break;
        }

        List<Label> allLabels = postController.getAllLabels();
        List<Long> idLabelsForNewPost = new ArrayList<>();

        System.out.println();
        System.out.println("Выберите тэги для новой статьи из списка существующих: ");
        System.out.println();

        for (Label label : allLabels) {
            System.out.println(label);
        }

        System.out.println("Введите id тэга для статьи, для завершения введите 0: ");

        while ((labelId = scanner.nextLong()) > 0) {
            idLabelsForNewPost.add(labelId);
        }

        postController.onCreate(content, postStatus, idLabelsForNewPost, writerId);

    }

    public void eventGetPostById() {
        Long id = 0L;

        System.out.println("===========================");
        System.out.println("Введите id искомой статьи: ");
        System.out.println("===========================");

        Post getPost;

        while (true) {
            id = scanner.nextLong();
            try {
                getPost = postController.getById(id);
                break;
            } catch (SQLException e) {
                System.out.println("Статьи с указанным id не существует! Повторите ввод.");

            }
        }

        System.out.println('\n');
        System.out.println("================");
        System.out.println("ИСКОМАЯ СТАТЬЯ: ");
        System.out.println("=========================================================================================");
        System.out.println(getPost.toStringAllData());
        System.out.println("=========================================================================================");
        System.out.println('\n');
    }

    public void eventUpdatePostById() {
        Long id = 0L;
        String content = "";
        PostStatus postStatus = null;
        int postStatusNum;
        Long writerId;

        System.out.println("============================================");
        System.out.println("Введите id статьи, которую хотите изменить: ");
        System.out.println("============================================");

        Post getPost;

        while (true) {
            id = scanner.nextLong();
            try {
                getPost = postController.getById(id);
                break;
            } catch (SQLException e) {
                System.out.println("Статьи с указанным id не существует! Повторите ввод.");

            }
        }

        System.out.println("===========================");
        System.out.println("Статья, которая будет изменена:");
        System.out.println("===========================================================");
        System.out.println(getPost.toStringAllData());
        System.out.println("===========================================================");
        System.out.println();
        System.out.println("==========================================");
        System.out.println("Введите новые данные для выбранной статьи:");
        System.out.println("==========================================");
        System.out.println();

        System.out.println("Введите новое название статьи: ");
        System.out.println();
        scanner.nextLine();
        content = scanner.nextLine();

        System.out.println();

        System.out.println("Укажите автора новой статьи из списка существующих: ");
        List<Writer> writers = postController.getAllWriters();
        System.out.println();

        for (Writer allWriter : writers) {
            System.out.println(allWriter);
        }
        System.out.println();

        System.out.println("Введите id автора статьи: ");

        writerId = scanner.nextLong();
        System.out.println();

        System.out.println("Укажите новый статус статьи из списка доступных, введя номер статуса: ");

        System.out.println("1. ACTIVE " + "\n" +
                           "2. UNDER_REVIEW " + "\n" +
                           "3. DELETED " + "\n");

        postStatusNum = scanner.nextInt();

        switch (postStatusNum) {
            case 1:
                postStatus = PostStatus.ACTIVE;
                break;
            case 2:
                postStatus = PostStatus.UNDER_REVIEW;
                break;
            case 3:
                postStatus = PostStatus.DELETED;
                break;
        }

        Post editPost = postController.onUpdate(id, content, postStatus, writerId);

        System.out.println("=======================================");
        System.out.println("Статья с id = " + id + " была изменена:");
        System.out.println("========================================================");
        System.out.println(editPost.toString());
        System.out.println("========================================================");
    }

    public void eventDeletePostById() {
        Long id = 0L;

        System.out.println("===========================================");
        System.out.println("Введите id статьи, которую хотите удалить: ");
        System.out.println("===========================================");

        id = scanner.nextLong();

        boolean deletePost = postController.onDelete(id);

        if (deletePost) {
            System.out.println("======================================");
            System.out.println("Статья с id = " + id + " была удалена!");
            System.out.println("======================================");
            System.out.println('\n');
        } else {
            System.out.println('\n');
            System.out.println("=============================================================================================================");
            System.out.println("WARNING! Статьи с указанным id не существует! Повторите попытку");
            System.out.println("=============================================================================================================");
            System.out.println('\n');
            show();
        }
    }


}

