import React from 'react';
import { Gift, Heart, Sparkles } from 'lucide-react';
import { BIRTHDAY_CONFIG } from '../config';
import { CountdownTimer } from './CountdownTimer';

interface IntroSectionProps {
  onOpenSurprise: () => void;
  customGreeting?: string;
  customSubtitle?: string;
  birthdayDate?: string;
  showCountdown?: boolean;
}

export const IntroSection: React.FC<IntroSectionProps> = ({
  onOpenSurprise,
  customGreeting,
  customSubtitle,
  birthdayDate = BIRTHDAY_CONFIG.birthdayDate,
  showCountdown = true,
}) => {
  return (
    <div className="flex flex-col items-center justify-center min-h-[85vh] px-5 py-8 text-center relative z-10 max-w-md mx-auto">
      {/* Animated Gift Hero */}
      <div className="relative mb-6 group cursor-pointer" onClick={onOpenSurprise}>
        {/* Glowing backdrop */}
        <div className="absolute -inset-4 bg-gradient-to-r from-rose-400 to-pink-300 rounded-full blur-xl opacity-60 group-hover:opacity-90 transition duration-1000 animate-pulse" />
        
        {/* Gift Circle */}
        <div className="relative w-32 h-32 rounded-full bg-gradient-to-tr from-rose-500 to-pink-400 p-1 shadow-2xl flex items-center justify-center transform transition duration-500 hover:scale-110 active:scale-95">
          <div className="w-full h-full rounded-full bg-gradient-to-br from-rose-600 via-rose-500 to-pink-500 flex items-center justify-center border-2 border-white/50 shadow-inner">
            <Gift className="w-16 h-16 text-white stroke-[1.8] animate-bounce" />
          </div>
          
          {/* Heart Badge */}
          <div className="absolute -top-1 -right-1 w-9 h-9 rounded-full bg-amber-400 border-2 border-white flex items-center justify-center shadow-md animate-pulse">
            <Heart className="w-5 h-5 text-rose-600 fill-rose-600" />
          </div>
        </div>
      </div>

      {/* Greeting Card */}
      <div className="w-full bg-white/90 backdrop-blur-md rounded-3xl p-6 sm:p-7 shadow-xl border border-rose-100 mb-6 transform transition duration-500 hover:shadow-2xl">
        <div className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-rose-50 border border-rose-200/60 text-rose-600 text-xs font-semibold mb-3 tracking-wider uppercase">
          <Sparkles className="w-3.5 h-3.5 text-rose-500" />
          <span>A Special Delivery</span>
          <Sparkles className="w-3.5 h-3.5 text-rose-500" />
        </div>

        <h1 className="font-serif-romantic text-3xl sm:text-4xl font-bold text-rose-950 mb-2 leading-tight tracking-tight">
          {customGreeting || BIRTHDAY_CONFIG.intro.greeting}
        </h1>

        <p className="text-rose-900/80 text-base sm:text-lg font-light leading-relaxed">
          {customSubtitle || BIRTHDAY_CONFIG.intro.subtitle}
        </p>

        {/* ⏰ Anticipation Countdown Timer */}
        {showCountdown && birthdayDate && (
          <CountdownTimer targetDate={birthdayDate} />
        )}

        <div className="mt-3 pt-3 border-t border-rose-100 flex items-center justify-center gap-2 text-rose-400 text-sm font-handwriting text-xl">
          <span>Made with love just for you</span>
          <Heart className="w-4 h-4 fill-rose-400 text-rose-400" />
        </div>
      </div>

      {/* Animated Action Button */}
      <button
        onClick={onOpenSurprise}
        className="w-full sm:w-auto px-8 py-4 rounded-full bg-gradient-to-r from-rose-600 via-rose-500 to-pink-500 text-white font-semibold text-lg tracking-wide shadow-lg shadow-rose-500/30 transform transition duration-300 hover:scale-105 hover:shadow-rose-500/50 active:scale-95 flex items-center justify-center gap-3 border border-white/30"
      >
        <span>{BIRTHDAY_CONFIG.intro.buttonText}</span>
      </button>

      <p className="text-xs text-rose-400/80 mt-4 font-light">
        Tap the button to start the magic ✨
      </p>
    </div>
  );
};
