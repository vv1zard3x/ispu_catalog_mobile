package com.example.vv1zard3x.data.remote

import com.example.vv1zard3x.data.model.Actor
import com.example.vv1zard3x.data.model.Genre
import com.example.vv1zard3x.data.model.Movie
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Мок-реализация API для демонстрации приложения.
 * Замените на реальный Retrofit клиент при наличии бэкенда.
 */
@Singleton
class MockMovieApi @Inject constructor() : MovieApi {

    private val mockGenres = listOf(
        Genre(28, "Боевик"),
        Genre(12, "Приключения"),
        Genre(16, "Мультфильм"),
        Genre(35, "Комедия"),
        Genre(80, "Криминал"),
        Genre(99, "Документальный"),
        Genre(18, "Драма"),
        Genre(10751, "Семейный"),
        Genre(14, "Фэнтези"),
        Genre(36, "История"),
        Genre(27, "Ужасы"),
        Genre(10402, "Музыка"),
        Genre(9648, "Детектив"),
        Genre(10749, "Мелодрама"),
        Genre(878, "Фантастика"),
        Genre(53, "Триллер"),
        Genre(10752, "Военный"),
        Genre(37, "Вестерн")
    )

    private val mockMovies = listOf(
        Movie(
            id = 1,
            title = "Начало",
            overview = "Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадёт ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим.",
            posterPath = "https://image.tmdb.org/t/p/w500/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
            rating = 8.4f,
            releaseDate = "2010-07-16",
            genreIds = listOf(28, 878, 53),
            voteCount = 34521
        ),
        Movie(
            id = 2,
            title = "Интерстеллар",
            overview = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину в поисках планеты с подходящими для человечества условиями.",
            posterPath = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            rating = 8.7f,
            releaseDate = "2014-11-07",
            genreIds = listOf(12, 18, 878),
            voteCount = 32145
        ),
        Movie(
            id = 3,
            title = "Тёмный рыцарь",
            overview = "Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы Готэма от преступности.",
            posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/nMKdUUepR0i5zn0y1T4CsSB5chy.jpg",
            rating = 9.0f,
            releaseDate = "2008-07-18",
            genreIds = listOf(28, 80, 18),
            voteCount = 30125
        ),
        Movie(
            id = 4,
            title = "Матрица",
            overview = "Жизнь Томаса Андерсона разделена на две части: днём он — Loss Angeles-ский программист, ночью — хакер, известный как Нео.",
            posterPath = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/fNG7i7RqMErkcqhohV2a6cV1Ehy.jpg",
            rating = 8.7f,
            releaseDate = "1999-03-31",
            genreIds = listOf(28, 878),
            voteCount = 24567
        ),
        Movie(
            id = 5,
            title = "Бойцовский клуб",
            overview = "Сотрудник страховой компании страдает от хронической бессонницы. Однажды он встречает торговца мылом Тайлера Дёрдена.",
            posterPath = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/rr7E0NoGKxvbkb89eR1GwfoYjpA.jpg",
            rating = 8.8f,
            releaseDate = "1999-10-15",
            genreIds = listOf(18, 53),
            voteCount = 26789
        ),
        Movie(
            id = 6,
            title = "Форрест Гамп",
            overview = "От лица главного героя Форреста Гампа, слабоумного мужчины с добрым сердцем, зритель наблюдает за событиями нескольких десятилетий американской истории.",
            posterPath = "https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/3h1JZGDhZ8nzxdgvkxha0qBqi05.jpg",
            rating = 8.8f,
            releaseDate = "1994-07-06",
            genreIds = listOf(35, 18, 10749),
            voteCount = 25678
        ),
        Movie(
            id = 7,
            title = "Властелин колец: Возвращение короля",
            overview = "Повелитель сил тьмы Саурон направляет свою бесчисленную армию на Средиземье. Несмотря на все препятствия, Фродо и Сэм продолжают путь к Роковой горе.",
            posterPath = "https://image.tmdb.org/t/p/w500/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/lXhgCODAbBXL5buk9yEmTpOoOgR.jpg",
            rating = 8.9f,
            releaseDate = "2003-12-17",
            genreIds = listOf(12, 14, 28),
            voteCount = 22345
        ),
        Movie(
            id = 8,
            title = "Гладиатор",
            overview = "Максимус — влиятельный римский полководец, любимец императора Марка Аврелия. После смерти императора наследник престола Коммод приказывает казнить Максимуса.",
            posterPath = "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/Ar7QuJfbJkCd3UH1T3cj3MnQn86.jpg",
            rating = 8.5f,
            releaseDate = "2000-05-05",
            genreIds = listOf(28, 18, 36),
            voteCount = 16789
        ),
        Movie(
            id = 9,
            title = "Список Шиндлера",
            overview = "История о том, как немецкий бизнесмен Оскар Шиндлер спас более тысячи польских евреев от уничтожения во время Холокоста.",
            posterPath = "https://image.tmdb.org/t/p/w500/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/loRmRzQXZeqG78TqZuyvSlEQfZb.jpg",
            rating = 9.0f,
            releaseDate = "1993-12-15",
            genreIds = listOf(18, 36, 10752),
            voteCount = 14567
        ),
        Movie(
            id = 10,
            title = "Побег из Шоушенка",
            overview = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Несмотря на то, что улики против него весьма сомнительные, он приговорён к пожизненному заключению.",
            posterPath = "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg",
            rating = 9.3f,
            releaseDate = "1994-09-23",
            genreIds = listOf(18, 80),
            voteCount = 25890
        ),
        Movie(
            id = 11,
            title = "Аватар",
            overview = "Бывший морпех Джейк Салли прикован к инвалидному креслу. Несмотря на немощное тело, Джейк в душе по-прежнему остаётся воином.",
            posterPath = "https://image.tmdb.org/t/p/w500/jRXYjXNq0Cs2TcJjLkki24MLp7u.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/o0s4XsEDfDlvit5pDRKjzXR4pp2.jpg",
            rating = 7.6f,
            releaseDate = "2009-12-18",
            genreIds = listOf(28, 12, 14, 878),
            voteCount = 29456
        ),
        Movie(
            id = 12,
            title = "Титаник",
            overview = "В первом и последнем плавании шикарного «Титаника» встречаются двое — молодой художник Джек и девушка Роза из высшего общества.",
            posterPath = "https://image.tmdb.org/t/p/w500/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w1280/kHXEpyfl6zqn8a6YuozZUujufXf.jpg",
            rating = 7.9f,
            releaseDate = "1997-12-19",
            genreIds = listOf(18, 10749),
            voteCount = 23567
        )
    )

    private val mockActors = mapOf(
        1 to listOf(
            Actor(1, "Леонардо ДиКаприо", "Кобб", "https://image.tmdb.org/t/p/w185/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"),
            Actor(2, "Джозеф Гордон-Левитт", "Артур", "https://image.tmdb.org/t/p/w185/dhv9v8KTxLJZDwpMpxDBjfCH8J4.jpg"),
            Actor(3, "Эллен Пейдж", "Ариадна", "https://image.tmdb.org/t/p/w185/bqHVcgY7a92UVj2wQxWxA5HItSL.jpg"),
            Actor(4, "Том Харди", "Имс", "https://image.tmdb.org/t/p/w185/d81K0RH8UX7tZj49tZaQhZ9ewH.jpg"),
            Actor(5, "Кен Ватанабэ", "Сайто", "https://image.tmdb.org/t/p/w185/psAXOYp9SBOXvg6AXzARDedNQ3v.jpg")
        ),
        2 to listOf(
            Actor(6, "Мэттью МакКонахи", "Купер", "https://image.tmdb.org/t/p/w185/wJiGedOCZhwMx9DezV6kCA5FsEm.jpg"),
            Actor(7, "Энн Хэтэуэй", "Бренд", "https://image.tmdb.org/t/p/w185/tLelKoPNiyJCSEtQTz1FGv4TLGc.jpg"),
            Actor(8, "Джессика Честейн", "Мёрф", "https://image.tmdb.org/t/p/w185/fDtgI4wKvU8IVqW0lQ3VTHqkxR.jpg"),
            Actor(9, "Майкл Кейн", "Профессор Бренд", "https://image.tmdb.org/t/p/w185/bVZRMlpjTAO2pJK6v90uSMa1RK.jpg")
        ),
        3 to listOf(
            Actor(10, "Кристиан Бейл", "Брюс Уэйн / Бэтмен", "https://image.tmdb.org/t/p/w185/qCpZn2e3dimwbryLnqxZuI88PTi.jpg"),
            Actor(11, "Хит Леджер", "Джокер", "https://image.tmdb.org/t/p/w185/5Y9HnYYa9jF4NunY9lSgJGjSe8E.jpg"),
            Actor(12, "Аарон Экхарт", "Харви Дент", "https://image.tmdb.org/t/p/w185/hTMYsq8bNy5gfXbPgUb0oLhYMnN.jpg"),
            Actor(13, "Мэгги Джилленхол", "Рэйчел Доуз", "https://image.tmdb.org/t/p/w185/xVHrHBnxfrfM0lxUwMrOBLo7NQU.jpg")
        ),
        4 to listOf(
            Actor(14, "Киану Ривз", "Нео", "https://image.tmdb.org/t/p/w185/4D0PpNI0kmP58hgrwGC3wCjxhnm.jpg"),
            Actor(15, "Лоренс Фишборн", "Морфеус", "https://image.tmdb.org/t/p/w185/8suOhUmPbfKqDQ17jQ1Gy0mI3P4.jpg"),
            Actor(16, "Кэрри-Энн Мосс", "Тринити", "https://image.tmdb.org/t/p/w185/xD4jTA3KmVP5469ChH67shM5lJ.jpg"),
            Actor(17, "Хьюго Уивинг", "Агент Смит", "https://image.tmdb.org/t/p/w185/nMqakRQxNPsRN2wOLMgLmgWrT49.jpg")
        ),
        5 to listOf(
            Actor(18, "Брэд Питт", "Тайлер Дёрден", "https://image.tmdb.org/t/p/w185/cckcYc2v0yh1tc9QjRelptcOBko.jpg"),
            Actor(19, "Эдвард Нортон", "Рассказчик", "https://image.tmdb.org/t/p/w185/5XBzD5WuTyVQZeS4II6gs1nn5P6.jpg"),
            Actor(20, "Хелена Бонэм Картер", "Марла Сингер", "https://image.tmdb.org/t/p/w185/DDeITcCpnBd0CkAIRPhggy9bt5.jpg")
        ),
        6 to listOf(
            Actor(21, "Том Хэнкс", "Форрест Гамп", "https://image.tmdb.org/t/p/w185/xndWFsBlClOJFRdhSt4NBwiPq2o.jpg"),
            Actor(22, "Робин Райт", "Дженни Каррен", "https://image.tmdb.org/t/p/w185/cGLFLdFjea4VhLmJfzIIp1FWglS.jpg"),
            Actor(23, "Гэри Синиз", "Лейтенант Дэн", "https://image.tmdb.org/t/p/w185/ngr6O6OhVlUHjYXzMGPCH7HGS2c.jpg")
        ),
        7 to listOf(
            Actor(24, "Элайджа Вуд", "Фродо", "https://image.tmdb.org/t/p/w185/7UKRbJBNG7mxBl2Ev3yA4doXhej.jpg"),
            Actor(25, "Вигго Мортенсен", "Арагорн", "https://image.tmdb.org/t/p/w185/vH5gVSpHAMhDaFWfh0Q7BG61O1y.jpg"),
            Actor(26, "Иэн МакКеллен", "Гэндальф", "https://image.tmdb.org/t/p/w185/5cnnnpnJG6TiYUSS7qgJheUmY4h.jpg"),
            Actor(27, "Орландо Блум", "Леголас", "https://image.tmdb.org/t/p/w185/gWzfvfIHFTnLHKylp8TkFbCMepm.jpg")
        ),
        8 to listOf(
            Actor(28, "Рассел Кроу", "Максимус", "https://image.tmdb.org/t/p/w185/uxiDoZa6hwlyUAHflGfx3QxoQoB.jpg"),
            Actor(29, "Хоакин Феникс", "Коммод", "https://image.tmdb.org/t/p/w185/ls9yVqxMB8m30MgUXaXYLplF9Qe.jpg"),
            Actor(30, "Конни Нильсен", "Лусилла", "https://image.tmdb.org/t/p/w185/bi6XQtf3rvXlxZi6rP4lxqJSKqJ.jpg")
        ),
        9 to listOf(
            Actor(31, "Лиам Нисон", "Оскар Шиндлер", "https://image.tmdb.org/t/p/w185/bboldwqSC6tdRi4aSl7JaGU1xKq.jpg"),
            Actor(32, "Бен Кингсли", "Ицхак Штерн", "https://image.tmdb.org/t/p/w185/vQtBqpF2HDdzbfXHDzR4u37i1Ac.jpg"),
            Actor(33, "Рэйф Файнс", "Амон Гёт", "https://image.tmdb.org/t/p/w185/tJr9GcmGNHhLVVEkinmNrVdOOGw.jpg")
        ),
        10 to listOf(
            Actor(34, "Тим Роббинс", "Энди Дюфрейн", "https://image.tmdb.org/t/p/w185/hsCu1JUzQQ4pl7uFxAVFLOs9yHh.jpg"),
            Actor(35, "Морган Фриман", "Рэд", "https://image.tmdb.org/t/p/w185/oIciQWr8VwKoR8TmAw5n3lxgaEb.jpg"),
            Actor(36, "Боб Гантон", "Начальник тюрьмы", "https://image.tmdb.org/t/p/w185/u6sQhrdHh5HzahXnq4jhbIuEGlQ.jpg")
        ),
        11 to listOf(
            Actor(37, "Сэм Уортингтон", "Джейк Салли", "https://image.tmdb.org/t/p/w185/mflBcox36s9ZPbsZPVHeVPPz1gz.jpg"),
            Actor(38, "Зои Салдана", "Нейтири", "https://image.tmdb.org/t/p/w185/iOVbUH20il632nj2v01NCtYYeSg.jpg"),
            Actor(39, "Сигурни Уивер", "Грейс Огустин", "https://image.tmdb.org/t/p/w185/flfhep27iBxseZIlxANLjdMFJuM.jpg")
        ),
        12 to listOf(
            Actor(1, "Леонардо ДиКаприо", "Джек Доусон", "https://image.tmdb.org/t/p/w185/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg"),
            Actor(40, "Кейт Уинслет", "Роза Дьюит", "https://image.tmdb.org/t/p/w185/e3tdop3WhseRnn8KwMVLq15LPVy.jpg"),
            Actor(41, "Билли Зейн", "Кэлдон Хокли", "https://image.tmdb.org/t/p/w185/9HIluHCEuLPlBp81yDPhFhyBqkY.jpg")
        )
    )

    override suspend fun getPopularMovies(): List<Movie> {
        delay(500) // Имитация сетевой задержки
        return mockMovies
    }

    override suspend fun getMovieDetails(movieId: Int): Movie? {
        delay(300)
        return mockMovies.find { it.id == movieId }
    }

    override suspend fun getMovieCast(movieId: Int): List<Actor> {
        delay(300)
        return mockActors[movieId] ?: emptyList()
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        delay(400)
        return mockMovies.filter { 
            it.title.contains(query, ignoreCase = true) || 
            it.overview.contains(query, ignoreCase = true) 
        }
    }

    override suspend fun getGenres(): List<Genre> {
        delay(200)
        return mockGenres
    }
}
