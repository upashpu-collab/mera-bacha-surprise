"""
🎂 BIRTHDAY SURPRISE WEBSITE - COMPLETE STANDALONE STREAMLIT APP
===============================================================
यह एक कंप्लीट और फ़ास्ट लोड होने वाली स्टैंडअलोन Python Streamlit वेबसाइट है।
इसमें काउंटडाउन टाइमर, प्यार भरा खत (Love Letter), 6 यादगार यादें, और
म्यूजिक बॉक्स की पूरी सुविधाएं शामिल हैं।

🚀 इसे रन करने का तरीका:
1. pip install -r requirements.txt
2. streamlit run streamlit_app.py
"""

import streamlit as st
import time
from datetime import datetime, timedelta

# Page Configuration - Mobile-optimized, clean aesthetic
st.set_page_config(
    page_title="Happy Birthday, My Love ❤️",
    page_icon="🎂",
    layout="centered",
    initial_sidebar_state="collapsed"
)

# Custom Romantic Theme & Smooth CSS
st.markdown("""
<style>
    @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,600;0,700;1,400&family=Dancing+Script:wght@700&family=Outfit:wght@300;400;500;600&family=Caveat:wght@700&display=swap');

    /* Background and global typography */
    .stApp {
        background: linear-gradient(180deg, #FFF8F6 0%, #FFF0F3 50%, #FFF8F6 100%);
        font-family: 'Outfit', sans-serif;
        color: #3E1F27;
    }

    h1, h2, h3 {
        font-family: 'Playfair Display', Georgia, serif;
        color: #881337 !important;
    }

    /* Romantic Card Styling */
    .romantic-card {
        background: rgba(255, 255, 255, 0.95);
        border: 1.5px solid #FFE4E8;
        border-radius: 24px;
        padding: 24px;
        box-shadow: 0 10px 30px rgba(225, 29, 72, 0.08);
        margin-bottom: 20px;
        text-align: center;
    }

    /* Countdown Digits Grid */
    .countdown-grid {
        display: flex;
        justify-content: center;
        gap: 12px;
        margin: 16px 0;
    }

    .countdown-box {
        background: #FFFFFF;
        border: 1px solid #FECDD3;
        border-radius: 16px;
        padding: 10px 14px;
        min-width: 65px;
        box-shadow: 0 4px 12px rgba(244, 63, 94, 0.08);
        text-align: center;
    }

    .countdown-number {
        font-family: 'Playfair Display', serif;
        font-size: 26px;
        font-weight: 700;
        color: #BE123C;
        line-height: 1;
    }

    .countdown-label {
        font-size: 10px;
        text-transform: uppercase;
        font-weight: 700;
        color: #FB7185;
        margin-top: 4px;
    }

    /* Love Letter Vintage Parchment */
    .letter-parchment {
        background: #FFFDFD;
        border: 2px dashed #FDA4AF;
        border-radius: 20px;
        padding: 24px;
        font-family: 'Playfair Display', Georgia, serif;
        font-size: 16px;
        line-height: 1.8;
        color: #4C0519;
        margin: 15px 0;
        box-shadow: 0 8px 24px rgba(225, 29, 72, 0.06);
    }

    .signature {
        font-family: 'Dancing Script', cursive;
        font-size: 26px;
        color: #E11D48;
        text-align: right;
        margin-top: 15px;
    }

    /* Polaroid Memory Cards */
    .polaroid {
        background: white;
        padding: 14px;
        border-radius: 18px;
        border: 1px solid #FFE4E8;
        box-shadow: 0 8px 20px rgba(0,0,0,0.04);
        text-align: center;
        margin-bottom: 16px;
    }

    .polaroid-badge {
        font-size: 38px;
        margin-bottom: 8px;
    }

    .polaroid-title {
        font-weight: 600;
        color: #9F1239;
        font-size: 15px;
        margin-bottom: 4px;
    }

    .polaroid-caption {
        font-size: 12px;
        color: #713F12;
        font-style: italic;
    }

    /* Streamlit Button Styling */
    div.stButton > button {
        background: linear-gradient(135deg, #E11D48 0%, #F43F5E 50%, #FB7185 100%) !important;
        color: white !important;
        border: none !important;
        border-radius: 9999px !important;
        padding: 12px 28px !important;
        font-size: 16px !important;
        font-weight: 600 !important;
        box-shadow: 0 8px 20px rgba(225, 29, 72, 0.25) !important;
        transition: all 0.3s ease !important;
        width: 100%;
    }
    div.stButton > button:hover {
        transform: translateY(-2px);
        box-shadow: 0 12px 25px rgba(225, 29, 72, 0.4) !important;
    }
</style>
""", unsafe_allow_html=True)

# Session State Initialization
if "step" not in st.session_state:
    st.session_state.step = 0
if "gf_name" not in st.session_state:
    st.session_state.gf_name = "My Love"
if "sender_name" not in st.session_state:
    st.session_state.sender_name = "Forever Yours"
if "target_time" not in st.session_state:
    # Default: 18 hours from now
    st.session_state.target_time = datetime.now() + timedelta(hours=18)

# Top Bar Navigation
st.markdown("<p style='text-align: center; font-size: 13px; color: #FB7185; letter-spacing: 2px; font-weight: 600; text-transform: uppercase;'>A Romantic Birthday Experience ❤️</p>", unsafe_allow_html=True)

# Step 0: INTRO SCREEN WITH LIVE COUNTDOWN TIMER
if st.session_state.step == 0:
    st.markdown("""
    <div style='text-align: center; margin-top: 15px; margin-bottom: 10px;'>
        <div style='display: inline-block; font-size: 55px; animation: bounce 2s infinite;'>🎁</div>
    </div>
    """, unsafe_allow_html=True)

    # Calculate Countdown
    now = datetime.now()
    diff = st.session_state.target_time - now
    total_seconds = int(diff.total_seconds())

    if total_seconds > 0:
        days = total_seconds // (24 * 3600)
        hours = (total_seconds % (24 * 3600)) // 3600
        mins = (total_seconds % 3600) // 60
        secs = total_seconds % 60

        countdown_html = f"""
        <div class="countdown-grid">
            <div class="countdown-box">
                <div class="countdown-number">{days:02d}</div>
                <div class="countdown-label">Days</div>
            </div>
            <div class="countdown-box">
                <div class="countdown-number">{hours:02d}</div>
                <div class="countdown-label">Hours</div>
            </div>
            <div class="countdown-box">
                <div class="countdown-number">{mins:02d}</div>
                <div class="countdown-label">Mins</div>
            </div>
            <div class="countdown-box">
                <div class="countdown-number">{secs:02d}</div>
                <div class="countdown-label">Secs</div>
            </div>
        </div>
        """
    else:
        countdown_html = """
        <div style='background: #FFF1F2; border: 1px solid #FDA4AF; border-radius: 16px; padding: 12px; margin: 15px 0; color: #BE123C; font-weight: bold;'>
            🎉 IT'S FINALLY YOUR OFFICIAL BIRTHDAY! 🎉
        </div>
        """

    st.markdown(f"""
    <div class="romantic-card">
        <span style='background: #FFF1F2; border: 1px solid #FECDD3; color: #BE123C; font-size: 11px; font-weight: 700; padding: 4px 12px; border-radius: 20px; text-transform: uppercase;'>A Special Delivery</span>
        <h1 style='margin-top: 12px; margin-bottom: 8px;'>Hey Beautiful ❤️</h1>
        <p style='color: #881337; font-size: 16px; opacity: 0.85; margin-bottom: 4px;'>
            Someone who loves you dearly has prepared a little surprise for you...
        </p>
        
        <!-- Live Countdown -->
        <p style='font-size: 12px; font-weight: 600; color: #BE123C; text-transform: uppercase; margin-top: 15px; margin-bottom: 2px;'>
            ⏰ Hours Left Until Your Official Birthday:
        </p>
        {countdown_html}
        <p style='font-size: 12px; font-style: italic; color: #FB7185;'>Counting every second until your magical moment ✨</p>
    </div>
    """, unsafe_allow_html=True)

    if st.button("OPEN YOUR SURPRISE 🎁", key="btn_open"):
        st.session_state.step = 1
        st.rerun()

# Step 1: REVEAL SCREEN WITH BALLOONS & CELEBRATION
elif st.session_state.step == 1:
    st.balloons()
    st.markdown(f"""
    <div class="romantic-card">
        <div style='font-size: 60px; margin-bottom: 10px;'>🎂</div>
        <span style='background: #FFF1F2; border: 1px solid #FECDD3; color: #BE123C; font-size: 11px; font-weight: 700; padding: 4px 12px; border-radius: 20px; text-transform: uppercase;'>It's Your Day!</span>
        <h1 style='margin-top: 14px;'>Happy Birthday,<br><span style='color: #E11D48;'>{st.session_state.gf_name} ❤️</span></h1>
        <p style='color: #9F1239; font-size: 16px; line-height: 1.6; font-style: italic; margin-top: 10px;'>
            "May your day be filled with endless smiles, warmth, and all the happiness in the world."
        </p>
        <div style='font-size: 24px; margin-top: 15px;'>🎉 ✨ 💖 🥂 🎈</div>
    </div>
    """, unsafe_allow_html=True)

    col1, col2 = st.columns(2)
    with col1:
        if st.button("⬅️ Back"):
            st.session_state.step = 0
            st.rerun()
    with col2:
        if st.button("Our Memories 📸 ➡️"):
            st.session_state.step = 2
            st.rerun()

# Step 2: 6 MEMORY POLAROID CARDS
elif st.session_state.step == 2:
    st.markdown("""
    <div style='text-align: center; margin-bottom: 20px;'>
        <h2>Moments That Made Me Fall in Love ✨</h2>
        <p style='color: #9F1239; font-size: 14px; font-style: italic;'>Cherished memories that live forever in my heart</p>
    </div>
    """, unsafe_allow_html=True)

    memories = [
        ("✨", "The Day We Met", "The moment my world became infinitely brighter and more colorful."),
        ("🌸", "Your Sweetest Smile", "Your laugh is honestly my most favorite sound in the whole universe."),
        ("🚗", "Our Adventures", "Every road we travel together turns into an unforgettable memory."),
        ("☕", "Quiet Moments", "Just holding your hand in peace is where I feel most at home."),
        ("💫", "Pure Magic With You", "Thank you for loving me so gently and unconditionally every day."),
        ("💍", "Forever & Always", "Growing old with you is my sweetest and greatest dream come true.")
    ]

    col1, col2 = st.columns(2)
    for idx, (emoji, title, caption) in enumerate(memories):
        target_col = col1 if idx % 2 == 0 else col2
        with target_col:
            st.markdown(f"""
            <div class="polaroid">
                <div class="polaroid-badge">{emoji}</div>
                <div class="polaroid-title">{title}</div>
                <div class="polaroid-caption">"{caption}"</div>
            </div>
            """, unsafe_allow_html=True)

    col1, col2 = st.columns(2)
    with col1:
        if st.button("⬅️ Back"):
            st.session_state.step = 1
            st.rerun()
    with col2:
        if st.button("Open Love Letter 💌 ➡️"):
            st.session_state.step = 3
            st.rerun()

# Step 3: LOVE LETTER
elif st.session_state.step == 3:
    st.markdown("""
    <div style='text-align: center; margin-bottom: 15px;'>
        <h2>A Letter From My Heart 💌</h2>
        <p style='color: #9F1239; font-size: 13px; font-style: italic;'>Private & Confidential</p>
    </div>
    """, unsafe_allow_html=True)

    st.markdown(f"""
    <div class="letter-parchment">
        <p style='font-weight: bold; font-size: 18px; color: #881337;'>To the love of my life, {st.session_state.gf_name},</p>
        <p>Today is all about you.</p>
        <p>Thank you for bringing so much happiness, love and beautiful moments into my life.</p>
        <p>I hope your birthday is as special and beautiful as you are.</p>
        <p style='font-weight: 600; color: #E11D48;'>Happy Birthday, My Love. ❤️</p>
        <div class="signature">
            With all my heart,<br>{st.session_state.sender_name} ❤️
        </div>
    </div>
    """, unsafe_allow_html=True)

    col1, col2 = st.columns(2)
    with col1:
        if st.button("⬅️ Back"):
            st.session_state.step = 2
            st.rerun()
    with col2:
        if st.button("One More Thing... ✨ ➡️"):
            st.session_state.step = 4
            st.rerun()

# Step 4: FINAL SURPRISE & REPLAY
elif st.session_state.step == 4:
    st.balloons()
    st.markdown(f"""
    <div class="romantic-card">
        <div style='font-size: 55px; margin-bottom: 10px;'>💖</div>
        <span style='background: #FFF1F2; border: 1px solid #FECDD3; color: #BE123C; font-size: 11px; font-weight: 700; padding: 4px 12px; border-radius: 20px; text-transform: uppercase;'>Forever & Always</span>
        <h1 style='margin-top: 14px;'>Happy Birthday,<br><span style='color: #E11D48;'>{st.session_state.gf_name} ❤️</span></h1>
        <p style='color: #4C0519; font-size: 16px; line-height: 1.8; margin-top: 15px;'>
            "You are my yesterday, my today, and all of my tomorrows. May this year bring you all the love, happiness, and dreams you truly deserve."
        </p>
        <p style='font-family: Dancing Script, cursive; font-size: 24px; color: #BE123C; margin-top: 20px;'>
            — {st.session_state.sender_name} ❤️
        </p>
    </div>
    """, unsafe_allow_html=True)

    if st.button("🔄 Replay Surprise From Start"):
        st.session_state.step = 0
        st.rerun()

# Sidebar: Quick Settings
with st.sidebar:
    st.markdown("### ✏️ Personalize Surprise")
    st.session_state.gf_name = st.text_input("Girlfriend's Name:", value=st.session_state.gf_name)
    st.session_state.sender_name = st.text_input("Your Name / Sign-off:", value=st.session_state.sender_name)
    
    st.markdown("### ⏰ Birthday Target Countdown")
    hours_left = st.slider("Hours left until birthday:", min_value=1, max_value=72, value=18)
    if st.button("Update Countdown Hours"):
        st.session_state.target_time = datetime.now() + timedelta(hours=hours_left)
        st.success("Countdown updated!")
        st.rerun()
