package com.example.app;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ProductService productService = new ProductService();
        TodoService todoService = new TodoService();

        while (true) {
            System.out.println("\n=== Catálogo & Tarefas ===");
            System.out.println("1. Listar produtos (Produtos)");
            System.out.println("2. Buscar produto por texto (Produtos)");
            System.out.println("3. Listar todos (Tarefas)");
            System.out.println("4. Adicionar todo (Tarefas)");
            System.out.println("5. Marcar/desmarcar todo (Tarefas)");
            System.out.println("6. Remover todo (Tarefas)");
            System.out.println("7. Sair");
            System.out.print("Escolha: ");

            if (!sc.hasNextLine()) {
                System.out.println("Erro: Entrada não disponível. Saindo...");
                return;
            }

            String inputLine = sc.nextLine();

            if (inputLine.isEmpty()) {
                continue;
            }

            try {
                int opcao = Integer.parseInt(inputLine.trim());

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Limit (Máx de resultados): ");
                        int limit = sc.nextInt();
                        System.out.print("Skip (Pular resultados): ");
                        int skip = sc.nextInt();
                        sc.nextLine();
                        productService.list(limit, skip).forEach(p ->
                                System.out.printf("%d | %s | R$%.2f%n", p.getId(), p.getTitle(), p.getPrice()));
                    }
                    case 2 -> {
                        System.out.print("Buscar (Termo de pesquisa): ");
                        String q = sc.nextLine();
                        productService.search(q).forEach(p ->
                                System.out.printf("%d | %s | R$%.2f%n", p.getId(), p.getTitle(), p.getPrice()));
                    }
                    case 3 -> {
                        System.out.print("Limit (Máx de resultados): ");
                        int limit = sc.nextInt();
                        System.out.print("Skip (Pular resultados): ");
                        int skip = sc.nextInt();
                        sc.nextLine();
                        todoService.list(limit, skip).forEach(t ->
                                System.out.printf("%d | %s | [%s] | user=%d%n",
                                        t.getId(), t.getTodo(), t.isCompleted() ? "OK" : "PEND", t.getUserId()));
                    }
                    case 4 -> {
                        System.out.print("Texto da nova tarefa: ");
                        String text = sc.nextLine();

                        System.out.print("UserId (ID do usuário): ");
                        int userId = sc.nextInt();
                        sc.nextLine();
                        Todo created = todoService.add(text, userId);
                        System.out.println("Criado: " + created);
                    }
                    case 5 -> {
                        System.out.print("ID da tarefa para alternar: ");
                        int id = sc.nextInt();
                        System.out.print("Marcar como completo? (true/false): ");
                        boolean completed = sc.nextBoolean();
                        sc.nextLine();
                        Todo updated = todoService.toggle(id, completed);
                        System.out.println("Atualizado: " + updated);
                    }
                    case 6 -> {
                        System.out.print("ID da tarefa para remover: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        boolean success = todoService.delete(id);
                        System.out.println(success ? "Removido com sucesso" : "Falha ao remover");
                    }
                    case 7 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Escolha entre 1 e 7.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, digite apenas o número da opção.");
            } catch (InputMismatchException e) {
                System.err.println("Erro de entrada: Por favor, insira o tipo de dado correto (número ou boolean).");
                sc.nextLine();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro na operação: " + e.getMessage());
            }
        }
    }
}
