package com.example.videoigre

class GameData {
    companion object {
        private val games = listOf(
            Game(
                "Hogwarts Legacy",
                "Microsoft Windows, PlayStation 4, PlayStation 5, Xbox One, Xbox Series X/S",
            "February 10, 2023",
                9.0,
                "https://www.playstation.com/en-us/games/hogwarts-legacy/",
                "T",
                "Avalanche Software",
                "Warner Bros. Interactive Entertainment",
                "Action",
                "Players take on the role of a student at Hogwarts School of Witchcraft and Wizardry in the late 1800s, exploring the school and its surrounding areas while learning spells and engaging in magical combat.",
                listOf(
                    UserRating("hermione12", 1614710400, 10.0),
                    UserRating("Lee", 1614451200, 8.0),
                    UserRating("Gryffindor", 1614182400, 9.0),
                    UserReview("gaamer1", 1614009600, "This is one of the best games I've ever played!"),
                    UserReview("gamer4life", 1613836800, "The open world is amazing.")
                )
            ),
            Game(
                "Super Mario Odyssey",
                "Nintendo Switch",
                "October 27, 2017",
                9.3,
                "https://example.com/mario_odyssey_cover.jpg",
                "E10+",
                "Nintendo EPD",
                "Nintendo",
                "Platformer",
                "Mario's latest adventure takes him on a globe-trotting journey to rescue Princess Peach from Bowser's wedding plans.",
                listOf(
                    UserRating("WilliamFletcher", 1614710400, 9.5),
                    UserRating("ranger_", 1614451200, 8.0),
                    UserRating("user123", 1614182400, 9.0),
                    UserReview("Stephann", 1614009600, "The new capture mechanic adds a ton of variety and fun to the gameplay."),
                    UserReview("games4life", 1613836800, "The game is great, but some of the moons can be a bit too easy to get.")
                )
            ),
            Game(
                "Spider-Man",
                "PlayStation 4, PlayStation 5",
                "September 7, 2018",
                8.7,
                "https://nesto.com/1RjJAvw.jpg",
            "T",
                "Insomniac Games",
                "Sony Interactive Entertainment",
                "Action-adventure",
                "Spider-Man is a video game based on the Marvel Comics superhero Spider-Man. Players take on the role of Peter Parker/Spider-Man, exploring an open world version of New York City while battling various enemies and supervillains.",
                listOf(
                    UserRating("Killer87", 1649251200, 9.5),
                    UserRating("BulletProof_1", 1648569600, 8.5),
                    UserReview("t1tanium", 1648280400, "Marvel's Spider-Man is one of the best games I have ever played, there are so many things that I enjoyed here that I wish I could play it all over again. It is a beautiful tribute to one of my favorite characters of all time."),
                    UserRating("PixelPundit", 1647602400, 8.0),
                    UserReview("AdventureAddict", 1646684400, "Storyline was AMAZING. It made you feel like you were Peter Parker and Spiderman. It has a lot of heart touching moments.")
                )
            ),
            Game(
                "Grand Theft Auto V",
                "PlayStation 4, Xbox One, PC",
                "September 17, 2013",
                8.7,
                "https://i.imgur.com/JL8pBfP.jpg",
                 "M",
                "Rockstar North",
                "Rockstar Games",
                "Action-Adventure",
                "In Grand Theft Auto V, players control three different characters who must work together to pull off a series of heists in the fictional city of Los Santos. The game features a massive open world filled with activities and missions, as well as a robust online multiplayer mode.",
                listOf(
                    UserRating("sabotage.5", 1649251200, 8.0),
                    UserReview("AccidentalGenius", 1648569600, "GTA V is a lot of fun, but it can also be frustrating at times. The story is good and the world is huge, but some of the missions can be a bit repetitive. The online multiplayer is where the real fun is at, though."),
                    UserRating("GameGuru", 1648280400, 9.0),
                    UserRating("MarkJohnson", 1647602400, 8.5),
                    UserReview("EmilyDavis", 1646684400, "I've been playing GTA V for years and I still love it. The story is great and the world is so detailed. The online multiplayer is where I spend most of my time, and there's always something new to do.")
                )
            ),
            Game(
                "Horizon Zero Dawn",
                "PlayStation 4, PC",
                "February 28, 2017",
                9.0,
                "https://i.imgur.com/M8hjwqs.jpg",
                "T",
                "Guerrilla Games",
                "Sony Interactive Entertainment",
                "Action RPG",
                "Horizon Zero Dawn is an action RPG set in a post-apocalyptic world where machines have taken over. Players control Aloy, a skilled hunter who sets out to uncover the truth about her past and the world she lives in. The game features a vast open world, thrilling combat, and a deep and engaging story.",
                listOf(
                    UserRating("GameGuru", 1649251200, 8.5),
                    UserRating("Alpha", 1648569600, 9.0),
                    UserReview("PixelPundit", 1648280400, "Horizon Zero Dawn is one of my favorite games of all time. The story is engaging, the world is beautiful, and the combat is so much fun. The only downside is that it can be a bit repetitive at times."),
                    UserRating("Angel2006", 1647602400, 8.0),
                    UserReview("CapitalA", 1646684400, "I really enjoyed Horizon Zero Dawn. The world is huge and there's always something new to discover. The combat is challenging but rewarding, and the story is interesting.")
                )
            ),
            Game(
                "Minecraft",
                "PC",
                "November 18, 2011",
                9.3,
                "https://minecraft.net/static/content/ogimage.jpg",
                "E10+",
                 "Mojang Studios",
                "Mojang Studios",
                "Sandbox",
                "Discover an infinite world of possibilities",
                emptyList()
            ),
            Game(
                "Red Dead Redemption 2",
                 "PlayStation 4",
                "October 26, 2018",
                 9.5,
                "https://images-na.ssl-images-amazon.com/images/I/81e4ZfQGadL._SL1500_.jpg",
                "M",
                "Rockstar Studios",
                "Rockstar Games",
                "Action-adventure",
                "America, 1899. The end of the Wild West era has begun",
                emptyList()
            ),
            Game(
                "The Last of Us Part II",
                "PlayStation 4, PlayStation 5",
                "June 19, 2020",
                9.5,
                "https://i.imgur.com/njKX8pA.jpg",
                "M",
                "Naughty Dog",
                "Sony Interactive Entertainment",
                "Action-Adventure",
                "The Last of Us Part II is a harrowing story about the human condition, survival, and the nature of relationships. Set in a post-apocalyptic world, the game follows the story of Ellie as she navigates a brutal and unforgiving world.",
                listOf(
                    UserRating("SarahSmith", 1649251200, 9.5),
                    UserReview("DavidLee", 1648569600, "The Last of Us Part II is a masterpiece. The story is heart-wrenching and the characters are unforgettable. The gameplay is tight and the graphics are stunning."),
                    UserRating("AshleyJohnson", 1648280400, 9.0),
                    UserRating("TroyBaker", 1647602400, 10.0),
                    UserReview("MariaGonzalez", 1646684400, "The Last of Us Part II is an emotional rollercoaster. The storytelling is superb and the gameplay is intense. The ending will stay with you long after you've finished the game.")
                )
            ),
            Game(
                "God of War",
                "PlayStation 4, PlayStation 5",
                "April 20, 2018",
                9.6,
                "https://i.imgur.com/UllU1ck.jpg",
                "M",
                "SIE Santa Monica Studio",
                "Sony Interactive Entertainment",
                "Action-Adventure",
                "God of War is a reimagining of the franchise that moves away from Greek mythology and into Norse mythology. Players control Kratos, now a father, as he sets out on a journey with his son Atreus to spread the ashes of his deceased wife.",
                listOf(
                    UserRating("MikeBrown", 1649251200, 9.0),
                    UserReview("MaggieWu", 1648569600, "God of War is an amazing game. The story is powerful and the gameplay is challenging. The graphics and sound design are also top-notch.")
                )
            ),
            Game(
                "Hitman 3",
                "PlayStation 4, PlayStation 5, Xbox One, Xbox Series X/S, Nintendo Switch",
                "January 20, 2021",
                8.8,
                "https://i.imgur.com/WyJdqD9.jpg",
                "M",
                "IO Interactive",
                "IO Interactive",
                "Action-adventure",
                " Players once again control Agent 47 as he completes contracts to eliminate high-profile targets in exotic locations around the world.",
                listOf(
                    UserRating("TomNook", 1649251200, 8.5),
                    UserReview("Isabelle", 1648569600, "Overall the game is pretty good. Each map has so many different ways to take out a target and I think the story is very interesting. The levels are looking better than they have ever looked before."),
                    UserRating("Timmy", 1648280400, 9.0),
                    UserRating("Tommy", 1647602400, 9.0),
                    UserReview("K.K. Slider", 1646684400, "IO really needs to work on the glitches. The cutscenes and the diversity of voices could be improved.")
                )
            )
        )

        fun getAll(): List<Game> {
            return games
        }

        fun getDetails(title: String): Game? {
            return games.find { it.title == title }
        }
    }
}