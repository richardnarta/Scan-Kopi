package com.android.scankopi.data.src.local

import com.android.scankopi.domain.model.TestResult

object Dummy {
    fun getRandomDummyTest(): TestResult {
        val data =  arrayListOf(
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 1 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 6. Kopi ini memenuhi syarat mutu 1 SNI dengan ketentuan jumlah nilai cacat pada rentang 0-11.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini memperoleh mutu Premium Grade karena memiliki jumlah nilai cacat sebesar 6. Kopi ini telah memenuhi syarat-syarat dari mutu Premium Grade yaitu jumlah nilai cacat maksimum 8, memiliki nilai cacat utama, pada biji roast maksimum terdapat 3 biji muda, cup evaluation maksimal 79.",
                detail = "3 biji berwarna hitam \n2 biji hitam sebagian \n1 kulit kopi berukuran besar",
                sni = 1,
                scaa = 1,
                sniDesc = "Mutu 1",
                scaaDesc = "Specialty Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 1 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 2. Kopi ini memenuhi syarat mutu 1 SNI dengan ketentuan jumlah nilai cacat pada rentang 0-11.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini memperoleh mutu Specialty Grade karena memiliki jumlah nilai cacat sebesar 2. Kopi ini telah memenuhi syarat-syarat dari mutu Specialty Grade yaitu jumlah nilai cacat maksimum 5, bebas dari nilai cacat utama, pada biji roast tidak terdapat biji muda, cup evaluation minimal 80.",
                detail = "5 kulit kopi berukuran kecil \n4 biji berwarna coklat",
                sni = 1,
                scaa = 1,
                sniDesc = "Mutu 1",
                scaaDesc = "Premium Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 2 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 21. Kopi ini memenuhi syarat mutu 2 SNI dengan ketentuan jumlah nilai cacat pada rentang 12-25.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 21.",
                detail = "10 biji berwarna hitam \n5 biji pecah \n5 biji muda \n16 biji hitam pecah \n2 biji hitam sebagian",
                sni = 2,
                scaa = 7,
                sniDesc = "Mutu 2",
                scaaDesc = "No Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 3 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 33. Kopi ini memenuhi syarat mutu 3 SNI dengan ketentuan jumlah nilai cacat pada rentang 26-44.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 33.",
                detail = "10 biji berwarna hitam\n4 biji hitam pecah\n2 biji hitam sebagian\n5 biji pecah\n10 biji berlubang satu\n1 ranting ukuran besar\n1 ranting ukuran sedang\n5 biji tutul-tutul (WP)",
                sni = 3,
                scaa = 7,
                sniDesc = "Mutu 3",
                scaaDesc = "No Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 4a berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 56. Kopi ini memenuhi syarat mutu 4a SNI dengan ketentuan jumlah nilai cacat pada rentang 45-60.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 56.",
                detail = "10 biji berwarna hitam\n6 biji hitam pecah\n4 biji hitam sebagian\n10 biji berlubang satu\n5 biji pecah\n5 biji muda\n1 ranting ukuran besar\n1 ranting ukuran sedang\n2 ranting ukuran kecil\n5 kulit tanduk ukuran sedang",
                sni = 4,
                scaa = 7,
                sniDesc = "Mutu 4a",
                scaaDesc = "No Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 4b berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 79. Kopi ini memenuhi syarat mutu 4b SNI dengan ketentuan jumlah nilai cacat pada rentang 61-80.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 79.",
                detail = "20 biji berwarna hitam\n10 biji hitam pecah\n6 biji hitam sebagian\n15 biji berlubang satu\n10 biji pecah\n5 biji muda\n1 ranting ukuran besar\n3 ranting ukuran sedang\n4 ranting ukuran kecil\n10 husk ukuran kecil\n5 biji berlubang lebih dari satu\n2 ranting ukuran sedang",
                sni = 5,
                scaa = 7,
                sniDesc = "Mutu 4b",
                scaaDesc = "No Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 5 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 83. Kopi ini memenuhi syarat mutu 5 SNI dengan ketentuan jumlah nilai cacat pada rentang 81-150.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 83.",
                detail = "3 biji berwarna hitam \n2 biji hitam sebagian \n2 biji hitam pecah \n1 husk kopi \n4 biji berwarna coklat \n1 husk ukuran besar \n2 husk ukuran sedang \n5 husk ukuran kecil \n10 biji berkulit ari (robusta WP) \n2 biji berkulit tanduk \n5 kulit tanduk ukuran sedang \n10 kulit tanduk ukuran kecil \n5 biji pecah \n5 biji muda \n10 biji berlubang satu \n5 biji berlubang lebih dari satu \n10 biji tutul-tutul (WP) \n1 ranting, tanah, batu ukuran besar \n1 ranting, tanah, batu ukuran sedang \n1 ranting, tanah, batu ukuran kecil",
                sni = 6,
                scaa = 7,
                sniDesc = "Mutu 5",
                scaaDesc = "No Grade"
            ),
            TestResult(
                description = "Kopi yang anda scan memiliki mutu 6 berdasarkan standar penilaian Standar Nasional Indonesia (SNI), dengan jumlah nilai cacat sebesar 197. Kopi ini memenuhi syarat mutu 6 SNI dengan ketentuan jumlah nilai cacat pada rentang 151-225.\n" +
                        "\n" +
                        "Selain itu, menurut standar SCAA, kopi ini tidak termasuk dalam mutu Specialty Grade dan Premium Grade karena memiliki jumlah nilai cacat sebesar 197.",
                detail = "120 biji berwarna hitam \n10 biji hitam sebagian \n10 biji hitam pecah \n10 husk kopi \n40 biji berwarna coklat \n5 husk ukuran besar \n5 husk ukuran sedang \n10 husk ukuran kecil \n10 biji berkulit ari (robusta WP) \n10 biji berkulit tanduk \n10 kulit tanduk ukuran sedang \n10 kulit tanduk ukuran kecil \n10 biji pecah \n10 biji muda \n10 biji berlubang satu \n10 biji berlubang lebih dari satu \n10 biji tutul-tutul (WP) \n1 ranting, tanah, batu ukuran besar \n1 ranting, tanah, batu ukuran sedang \n1 ranting, tanah, batu ukuran kecil",
                sni = 7,
                scaa = 7,
                sniDesc = "Mutu 6",
                scaaDesc = "No Grade"
            )
        )

        return data.random()
    }
}