/**
 * ============================================================================
 * 🎂 BIRTHDAY SURPRISE CONFIGURATION (Buttki ❤️ & Upash)
 * ============================================================================
 */

export interface MemoryPhotoItem {
  id: number;
  title: string;
  caption: string;
  emoji: string;
  customImage?: string;
  gradient: string;
}

export const BIRTHDAY_CONFIG = {
  // 💖 गर्लफ्रेंड और आपका नाम
  girlfriendName: "Buttki ❤️",
  senderName: "Upash",

  // ⏰ जन्मदिन की तारीख (रात 12:00 बजे के लिए)
  birthdayDate: new Date(Date.now() + 18 * 60 * 60 * 1000).toISOString(),
  showCountdown: true,

  // 🎁 इंट्रो स्क्रीन
  intro: {
    greeting: "Happy Birthday Meri Jaan ❤️",
    subtitle: "Mera bachha, aapke liye ek chhota sa surprise...",
    buttonText: "OPEN YOUR SURPRISE 🎁"
  },

  // 🎉 रिवील स्क्रीन
  reveal: {
    title: "Happy Birthday, Mera Bachha ❤️",
    subtitle: "Aaj ka din mere liye duniya ka sabse khoobsurat din hai kyunki aaj hi ke din duniya mein woh insaan aaya tha jisne meri life ko itna pyaara bana diya.",
    buttonText: "Continue →"
  },

  // 📸 यादगार फ़ोटोज़ 
  memories: [
    {
      id: 1,
      title: "The Day We Met ✨",
      caption: "Jab se aap meri life mein aaye, meri duniya aur bhi khoobsurat ho gayi.",
      emoji: "✨",
      customImage: "",
      gradient: "from-rose-400 to-pink-300"
    },
    {
      id: 2,
      title: "Aapki Pyari Smile 😊",
      caption: "Aapko dekhkar aur aapse baat karke mera poora din ban jaata hai.",
      emoji: "🥰",
      customImage: "",
      gradient: "from-pink-400 to-rose-300"
    },
    {
      id: 3,
      title: "Humari Mastiyan 🌸",
      caption: "Bahut saari mastiyan karni hain aur bahut saare dreams poore karne hain saath milkar.",
      emoji: "🌸",
      customImage: "",
      gradient: "from-amber-300 to-rose-300"
    },
    {
      id: 4,
      title: "Mera Sab Kuch 🌙",
      caption: "Aap meri sabse badi strength ho, mera motivation ho aur meri poori duniya ho.",
      emoji: "💫",
      customImage: "",
      gradient: "from-purple-400 to-pink-300"
    },
    {
      id: 5,
      title: "Forever Together 💖",
      caption: "Main chahe kisi bhi situation mein rahun, hamesha aapke saath khada rahunga.",
      emoji: "💝",
      customImage: "",
      gradient: "from-rose-400 to-red-300"
    },
    {
      id: 6,
      title: "Meri Ardhangini 🌟",
      caption: "Every lifetime, every time… I’ll choose YOU. ♾️❤️",
      emoji: "👑",
      customImage: "",
      gradient: "from-pink-500 to-rose-400"
    }
  ] as MemoryPhotoItem[],

  // 💌 प्यार भरा ख़त
  letter: {
    envelopePrompt: "Tap the envelope to open your letter 💌",
    title: "Happy Birthday meri Buttki 🎂❤️🧿",
    salutation: "Mera bachhaaa 🥹❤️,",
    body: `Mera bachha, aaj ka din mere liye duniya ka sabse khoobsurat din hai 🥹❤️ kyunki aaj hi ke din duniya mein woh insaan aaya tha jisne meri life ko itna pyaara, itna beautiful aur itna special bana diya… jise main kabhi words mein explain hi nahi kar paunga. 🫂❤️

Aapko shayad kabhi realise bhi nahi hua hoga ki aap mere liye kya ho… Aap mere liye bas baaki logon ki tarah ek girlfriend nahi ho. Aap meri sabse badi strength ho, mera sabse bada motivation ho aur mera sab kuch ho. ❤️

Aapse baat na karu toh mera din adhura-adhura sa lagta hai… aur aapko dekhna, aapko chaahna, aapke baare mein sochna… yaar, is feeling ko main words mein explain hi nahi kar sakta. 🥹❤️ Bas itna samajh lo ki aapse baat karke, aapko yaad karke, aapko dekhkar mera poora din ban jaata hai… ekdum pyaara sa din ho jaata hai. 🫶🏻💗

Uske baad meri saari tension, saari problems, saari pareshaani… sab kuch jaise door ho jaata hai. Kitni bhi dikkat ho, kitna bhi stress ho, bas aapse baat ho jaaye toh sab kuch halka-halka sa lagne lagta hai. 🥺❤️

Aap meri poori duniya ho yaar… 🌍❤️ Aapke hone se meri life kitni khoobsurat ho gayi hai, main bata bhi nahi sakta. Yaar, aap ho hi kya… matlab mere paas aapko describe karne ke liye words hi nahi hain. 🥹❤️

Main jaisa chahta tha, jaisa sochta tha ki meri life partner kaisi ho… jo mujhe hamesha motivate kare, har cheez mein mera saath de… Agar main koi galti karu toh mujhe samjhaye ki “ye galat hai, aap galat kar rahe ho” aur jab main kuch sahi karu toh mujhe appreciate kare… ❤️

Matlab yaar, jaisa maine socha tha, jaisa mera dream tha… ekdum waisi hi ho aap. 🥹🧿 Thodi naughty 😏, thodi gussa karne wali 😤😂, thodi cute si… aur pyaari toh pyaari se bhi zyada pyaari ho aap. 🥺❤️

Mera ek dream tha ki meri life partner kaisi ho… aur sach mein, aap bilkul waisi hi ho jaisa maine kabhi imagine kiya tha. 🧿❤️ Bas kisi ki nazar na lage meri is beautiful si dream life ko. 🥹🧿

Aur haan… main apni poori life aapke saath spend karna chahta hoon. ❤️👫🏻 Aapke saath bahut saari 🌚 mastiyan karni hain, saath mein ghoomna hai 🥰✈️, bahut saare dreams hain jo hum dono milkar complete karenge… aur ek din old age bhi aapke saath hi spend karni hai. 👴🏻👵🏻❤️ Hehehe 😚🤭

Main hamesha bas yahi chahta hoon ki kabhi bhi aapki aankhon mein aansu na aaye. 🥺❤️ Jab aap roti ho na, mujhe bilkul achha nahi lagta… main tension mein aa jaata hoon, mujhe bura lagta hai ki meri bachhi ki aankhon mein aansu kaise aa gaye. 🥺💔

Bas main aapki aankhon mein ek hi tarah ke aansu dekhna chahta hoon… woh bhi happiness ke tears. 🥹❤️✨

Mera bachha, ek baat hamesha yaad rakhna… zindagi mein chahe kitni bhi mushkilein aayein ya kitne bhi achhe-bure waqt aayein, main kabhi aapka haath nahi chhodunga. 🫂❤️

Aapko pata hi hoga, main kaisa ladka hoon… main jab kisi se ek baar promise kar deta hoon na, toh usse apni last breath tak nibhaane ki poori koshish karta hoon. ❤️

Main chahe kisi bhi situation mein rahun, life mujhe kahin bhi le jaaye, main hamesha aapke saath khada rehna chahta hoon. 🤝🏻❤️ Meri taraf se kabhi aapko ye feel nahi hona chahiye ki aap akeli ho. Main hamesha aapka saath dena chahta hoon. 🫂❤️

Aur bachha, kabhi bhi ye mat sochna ki main aapke saath cheating ya dhokha karunga… kabhi nahi. Aisa kabhi nahi ho sakta. ❤️🩹♾️

I LOVE YOU SOOO MUCHHH MERA BACHHAAA 😭❤️🫂💋

Meri jaan, aapko pata hai na… aap mere liye kitni zyada precious aur kitni special ho. 🥹❤️ Kabhi-kabhi main sochta hoon ki kisi insaan se itna strong attachment itni jaldi kaise ho sakta hai… itna pyaara bond kaise ban sakta hai. 🥺💗

Mujhe nahi pata aapne mere upar kya magic kiya hai yaar 😂❤️ but I just know one thing… I LOVE YOU SO MUCH. 🥹❤️

Thank you so much meri bachhi… ❤️ Thank you for loving me, understanding me, supporting me and making my life so much more beautiful. 🫂❤️

Bas aise hi mujhe apna pyaar dete rehna… 🥺❤️ Mujhe aur kuch nahi chahiye. Mujhe bas aapka pyaar, aapka saath aur aap chahiye. ❤️🫂 Bas. 🥹

Aur aaj mere Mahadev se meri bas yahi prayer hai 🙏🏻❤️ ki woh meri bachhi ko hamesha khush rakhein, hamesha healthy rakhein, life mein bahut aage le jaayein aur bahut successful banayein. 🧿✨

Hum dono milkar life mein bahut dhamaal machayein 😂❤️, jo-jo dreams humne dekhe hain sab complete karein, saath mein mandir jaayein 🛕, saath mein ghoomein 🥰✈️, bahut saari memories banayein 📸❤️ aur hamesha ek doosre ke saath khush rahein. 🫂❤️

Bas Bhagwan hum dono par hamesha apna aashirwad banaye rakhein 🙏🏻❤️ aur humare pyaar ko hamesha buri nazar se bachayein. 🧿❤️

Happiest, happiest, happiest Birthday meri Buttki! 🎂🥳❤️🧿

I LOVE YOU SOOOOO MUCH MERI ARDHANGINI ❤️🫂💋

Aur haan… mera pyaar aapke liye har din aur zyada badhta rahega. ❤️📈🥹

Aur agar mujhe dobara zindagi jeene ka chance mile… aur jitni baar bhi mile… main har baar aapko hi choose karunga apna partner banane ke liye. ❤️🥹♾️

Every lifetime, every time… I’ll choose YOU. ❤️🫂🧿

Once again… HAPPY BIRTHDAY MERI JAAN, MERI BUTTKI, MERA BACHHAAA! 🎂❤️🥹

I LOVE YOU SO MUCHHHHH ❤️♾️🫂💋`,
    closing: "Always, forever and for a lifetime. ♾️❤️\nTumhara Upash",
  },

  // 🎵 6. MUSIC SECTION (गाना / म्यूजिक)
  music: {
    title: "Romantic Birthday Melody",
    artist: "A special melody played just for Buttki 🎶",
    audioUrl: "", // If empty, the built-in acoustic music-box chime plays automatically!
    subtitle: "A soft, romantic music box chime composed for your special day."
  },

  // 🌟 7. FINAL SURPRISE SCREEN (आखरी सरप्राइज स्क्रीन)
  finalSurprise: {
    preTitle: "One More Thing... ❤️",
    revealButtonText: "Tap to Reveal Final Surprise ✨",
    mainTitle: "Happy Birthday, Mera Bachha ❤️",
    message: "You are my yesterday, my today, and all of my tomorrows. May Mahadev bless you with all the happiness, health and dreams you truly deserve. Happy Birthday Buttki!",
    replayButtonText: "Replay Surprise 🔄"
  }
};
