pluginManagement {
    repositories {
        // Inclui os plugins do Android e do Google
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        // Inclui bibliotecas e plugins padrão do Maven
        mavenCentral()
        // Portal oficial do Gradle para plugins
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Força o uso exclusivo dos repositórios definidos aqui (ignora repositórios nos builds dos módulos)
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Repositórios padrão para dependências
        google()
        mavenCentral()
    }
}

// Define o nome do projeto e inclui o módulo "app"
rootProject.name = "android-lista-de-compras"
include(":app")
