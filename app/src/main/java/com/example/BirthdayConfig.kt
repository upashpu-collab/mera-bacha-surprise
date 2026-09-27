package com.example

/**
 * ============================================================================
 * 🎂 BIRTHDAY SURPRISE CONFIGURATION (यहीं से अपनी जानकारी बदलें)
 * ============================================================================
 * If you are a beginner, you can change your girlfriend's name, message,
 * captions, and settings directly in this file OR inside the app by tapping
 * the "✏️ Edit / Customize" button!
 *
 * (अगर आप कोडिंग नहीं जानते, तो आप नीचे दिए गए नामों और मैसेजेस को आसानी से
 * बदल सकते हैं। बस quotes "" के अंदर का टेक्स्ट बदलें!)
 */
object BirthdayConfig {

    // 💖 1. Girlfriend's Name & Nickname (अपनी गर्लफ्रेंड का नाम यहाँ लिखें)
    const val GIRLFRIEND_NAME = "My Love" // उदा. "Pooja", "Simran", "Ananya", "My Princess"
    const val YOUR_NAME = "Forever Yours" // उदा. "Rahul", "Aman", etc.

    // 🎁 2. Intro Screen Texts (पहला स्क्रीन)
    const val INTRO_GREETING = "Hey Beautiful ❤️"
    const val INTRO_SUBTITLE = "Someone has prepared a little surprise for you..."
    const val INTRO_BUTTON = "OPEN YOUR SURPRISE 🎁"

    // 🎉 3. Birthday Reveal Texts (दूसरा स्क्रीन - गुब्बारे और कन्फेटी)
    const val REVEAL_TITLE = "Happy Birthday, My Love ❤️"
    const val REVEAL_SUBTITLE = "May your smile shine brighter than all the stars in the sky."
    const val REVEAL_BUTTON = "Continue →"

    // 📸 4. 6 Memories / Photos (6 यादगार फोटो और उनके नीचे के प्यार भरे संदेश)
    data class MemoryPhoto(
        val id: Int,
        val defaultTitle: String,
        val defaultCaption: String,
        val placeholderEmoji: String,
        val gradientColors: List<Long>
    )

    val DEFAULT_MEMORIES = listOf(
        MemoryPhoto(
            id = 1,
            defaultTitle = "The Day We Met ✨",
            defaultCaption = "The moment my world became infinitely more colorful and beautiful.",
            placeholderEmoji = "✨",
            gradientColors = listOf(0xFFFF9A9E, 0xFFFAD0C4)
        ),
        MemoryPhoto(
            id = 2,
            defaultTitle = "Your Sweetest Smile 🌸",
            defaultCaption = "Your laugh is honestly my favorite sound in the entire universe.",
            placeholderEmoji = "🌸",
            gradientColors = listOf(0xFFA18CD1, 0xFFFBC2EB)
        ),
        MemoryPhoto(
            id = 3,
            defaultTitle = "Our Adventures 🚗",
            defaultCaption = "Every little journey with you turns into an unforgettable memory.",
            placeholderEmoji = "✈️",
            gradientColors = listOf(0xFFFFECD2, 0xFFFCB69F)
        ),
        MemoryPhoto(
            id = 4,
            defaultTitle = "Quiet Moments ☕",
            defaultCaption = "Just holding your hand in silence brings me the deepest peace.",
            placeholderEmoji = "☕",
            gradientColors = listOf(0xFFFF8DA1, 0xFFFFC3A0)
        ),
        MemoryPhoto(
            id = 5,
            defaultTitle = "Pure Magic With You 💫",
            defaultCaption = "Thank you for loving me exactly the way you do every single day.",
            placeholderEmoji = "💫",
            gradientColors = listOf(0xFFFDA085, 0xFFF6D365)
        ),
        MemoryPhoto(
            id = 6,
            defaultTitle = "Forever & Always 💍",
            defaultCaption = "Growing old with you is my greatest dream come true.",
            placeholderEmoji = "💖",
            gradientColors = listOf(0xFFFF758C, 0xFFFF7EB3)
        )
    )

    // 💌 5. Love Letter Content (प्यार भरा खत / लव लेटर)
    const val LETTER_ENVELOPE_PROMPT = "Tap the envelope to open your letter 💌"
    const val LETTER_GREETING = "To the love of my life,"
    const val LETTER_BODY =
        "Today is all about you.\n\n" +
        "Thank you for bringing so much happiness, love and beautiful moments into my life.\n\n" +
        "I hope your birthday is as special and beautiful as you are.\n\n" +
        "Happy Birthday, My Love. ❤️"
    const val LETTER_SIGN_OFF = "With all my heart,\nForever Yours ❤️"

    // 🎵 6. Music Section (म्यूजिक सेक्शन)
    const val MUSIC_TITLE = "Romantic Birthday Melody"
    const val MUSIC_ARTIST = "Played just for you 🎶"
    const val MUSIC_SUBTITLE = "A soft, romantic music box chime composed for your special day."
    // Note: The app includes a built-in acoustic romantic music box synthesizer,
    // and you can also pick any MP3 song from your phone storage via the in-app player!

    // 🌟 7. Final Surprise Screen (आखरी सरप्राइज स्क्रीन)
    const val FINAL_PRE_TITLE = "One More Thing... ❤️"
    const val FINAL_REVEAL_BUTTON = "Tap to Reveal Final Surprise ✨"
    const val FINAL_MAIN_TITLE = "Happy Birthday, My Love ❤️"
    const val FINAL_MESSAGE = "You are my yesterday, my today, and all of my tomorrows. May this year bring you all the love, happiness, and dreams you truly deserve."
    const val FINAL_REPLAY_BUTTON = "Replay Surprise 🔄"
}
