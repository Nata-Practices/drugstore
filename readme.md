# 🏥 Drugstore App

### 📖 Описание

**Drugstore App** — это приложение для управления списком лекарств. Оно позволяет добавлять, редактировать, удалять и фильтровать лекарства, а также использовать сортировку по цене и производителю. Проект построен с использованием **Kotlin** и **Android SDK**.

---

## 🚀 Функционал

1. **📋 Список лекарств:**
   - Отображение лекарств в виде списка с описанием.
   - Используется `RecyclerView` для динамического отображения данных.

2. **➕ Добавление лекарства:**
   - Вызов диалогового окна для заполнения данных о новом лекарстве.

3. **✏️ Редактирование лекарства:**
   - Долгое нажатие на элемент вызывает меню с опцией редактирования.
   - Ввод данных в диалоговом окне с предварительным заполнением текущих данных.

4. **🗑️ Удаление лекарства:**
   - Удаление записи через контекстное меню.

5. **🔍 Фильтрация и поиск:**
   - Фильтрация по типу лекарства.
   - Поиск по названию лекарства.

6. **📊 Сортировка:**
   - Сортировка по цене (дешевле/дороже) и по производителю.

---

## 🗂️ Структура проекта

```
kotlin+java/
└── ru.kamazz.drugstore
    ├── models
    │   └── AptekaItem.kt          // Модель данных для лекарства
    ├── ui
    │   ├── MainActivity.kt        // Главная Activity приложения
    │   ├── adapter
    │   │   └── MyAdapter.kt       // Адаптер для RecyclerView
    │   └── dialogs
    │       ├── DialogCallback.kt  // Интерфейс для более удобной работы с диалогами
    │       └── EditDialog.kt      // Диалог для добавления/редактирования лекарства
    ├── utils
    │   ├── DataUtil.kt            // Утилита для работы с данными
    │   └── FilterUtil.kt          // Утилита для работы с сортировкой
    └── assets
        └── data.json              // JSON-файл с данными о лекарствах
```

### 🛠️ Описание основных модулей

#### 1. **`models`**
- **`AptekaItem.kt`**:
   - Модель лекарства с параметрами:
      - `nameLekarstva`: Название лекарства.
      - `proizvoditelLekarstva`: Производитель.
      - `vidLekarstva`: Тип лекарства.
      - `formaLekarstva`: Форма выпуска.
      - `obQemLekarstva`: Объем.
      - `stoimostLekarstva`: Стоимость.

#### 2. **`ui`**
- **`MainActivity.kt`**:
   - Главная Activity приложения.
   - Реализует логику отображения, фильтрации, сортировки и управления данными.
- **`dialogs/DialogCallback.kt`**:
   - Интерфейс для работы с диалогами.
   - Используется для обработки сохранения изменений после редактирования или добавления лекарства в `EditDialog`.
- **`dialogs/EditDialog.kt`**:
   - Диалоговое окно для добавления или редактирования лекарства.
   - Принимает `AptekaItem` для редактирования или создает новый элемент.
- **`adapter/MyAdapter.kt`**:
   - Адаптер для `RecyclerView`.
   - Обрабатывает отображение элементов списка и долгие нажатия на них.

#### 3. **`utils`**
- **`DataUtil.kt`**:
   - Обеспечивает операции над данными:
      - Загрузка из `data.json`.
      - Сохранение изменений.
      - Добавление/удаление/обновление элементов.
- **`FilterUtil.kt`**:
   - Утилита для фильтрации и сортировки списка лекарств.
   - Основной метод:
      - **`filterAndSort`**:
         - Фильтрует список по запросу и выбранному типу лекарства.
         - Сортирует список по выбранному критерию (`Дешевле`, `Дороже`, `Производитель`).

#### 4. **`assets`**
- **`data.json`**:
   - Исходный файл с данными о лекарствах, используемый для начальной загрузки.

---

## 📥 Установка и запуск

1. Склонируй репозиторий:
   ```bash
   git clone https://github.com/Nata-Practices/drugstore.git
   ```
2. Открой проект в Android Studio.
3. Запусти приложение на устройстве или эмуляторе.

---

## 📚 Как использовать

1. **👀 Просмотр списка:**
   - Лекарства отображаются в списке.
   - Каждый элемент содержит основную информацию.

2. **➕ Добавление нового лекарства:**
   - Нажмите кнопку "Добавить".
   - Заполните поля в диалоговом окне.
   - Нажмите "Сохранить".

3. **✏️ Редактирование лекарства:**
   - Долгое нажатие на элемент списка.
   - Выберите "Редактировать".
   - Измените данные в диалоговом окне.

4. **🗑️ Удаление лекарства:**
   - Долгое нажатие на элемент списка.
   - Выберите "Удалить".

5. **🔍 Фильтрация и сортировка:**
   - Выберите фильтр в выпадающем меню (тип лекарства).
   - Укажите критерий сортировки (дешевле, дороже, производитель).

---

## 📸 Скриншоты

### 🏠 Главная страница
На главной странице отображается список лекарств с функциями фильтрации, сортировки, поиска, а также кнопкой для добавления нового лекарства.

![Главная страница](https://github.com/user-attachments/assets/996ddabd-dbef-4338-b283-20405f3e87c2)

---

### ✏️ Редактирование элемента
В окне редактирования можно изменить данные выбранного лекарства. Поля автоматически заполняются текущими данными.

![Редактирование элемента](https://github.com/user-attachments/assets/eb0376d6-94dc-4bd8-9438-7090e9154c3c)

---