import React, { useState, useEffect } from 'react';
import { Clock, Sparkles, Heart } from 'lucide-react';

interface CountdownTimerProps {
  targetDate: string; // ISO date string or parseable format
  onReached?: () => void;
}

interface TimeRemaining {
  days: number;
  hours: number;
  minutes: number;
  seconds: number;
  totalMilliseconds: number;
  isReached: boolean;
}

export const CountdownTimer: React.FC<CountdownTimerProps> = ({ targetDate, onReached }) => {
  const calculateTimeRemaining = (): TimeRemaining => {
    const target = new Date(targetDate).getTime();
    const now = Date.now();
    const diff = target - now;

    if (isNaN(target) || diff <= 0) {
      return {
        days: 0,
        hours: 0,
        minutes: 0,
        seconds: 0,
        totalMilliseconds: 0,
        isReached: true,
      };
    }

    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((diff % (1000 * 60)) / 1000);

    return {
      days,
      hours,
      minutes,
      seconds,
      totalMilliseconds: diff,
      isReached: false,
    };
  };

  const [timeLeft, setTimeLeft] = useState<TimeRemaining>(calculateTimeRemaining());

  useEffect(() => {
    const timer = setInterval(() => {
      const remaining = calculateTimeRemaining();
      setTimeLeft(remaining);

      if (remaining.isReached && onReached) {
        onReached();
        clearInterval(timer);
      }
    }, 1000);

    return () => clearInterval(timer);
  }, [targetDate]);

  if (timeLeft.isReached) {
    return (
      <div className="w-full bg-gradient-to-r from-rose-500/10 via-pink-500/10 to-rose-500/10 border border-rose-300 rounded-2xl p-3.5 my-4 text-center animate-pulse">
        <div className="inline-flex items-center gap-1.5 text-rose-700 font-bold text-sm tracking-wide">
          <Sparkles className="w-4 h-4 text-amber-500 animate-spin" />
          <span>🎉 IT'S FINALLY YOUR BIRTHDAY! 🎉</span>
          <Sparkles className="w-4 h-4 text-amber-500 animate-spin" />
        </div>
        <p className="text-xs text-rose-600/90 font-medium mt-0.5">
          The wait is over, open your surprise now! ❤️
        </p>
      </div>
    );
  }

  // Format with leading zero
  const pad = (num: number) => String(num).padStart(2, '0');

  return (
    <div className="w-full my-5 bg-gradient-to-b from-white/95 to-rose-50/70 backdrop-blur-md rounded-2xl p-4 shadow-lg border border-rose-200/80">
      {/* Title */}
      <div className="flex items-center justify-center gap-1.5 text-xs font-semibold uppercase tracking-wider text-rose-700 mb-3">
        <Clock className="w-3.5 h-3.5 text-rose-500 animate-pulse" />
        <span>Countdown to the Official Birthday</span>
        <Heart className="w-3 h-3 fill-rose-500 text-rose-500" />
      </div>

      {/* Timer Digits Grid */}
      <div className="grid grid-cols-4 gap-2 text-center">
        {/* Days (if any) */}
        <div className="bg-white rounded-xl py-2 px-1 shadow-sm border border-rose-100 flex flex-col items-center">
          <span className="font-serif-romantic text-2xl sm:text-3xl font-extrabold text-rose-950 leading-none">
            {pad(timeLeft.days)}
          </span>
          <span className="text-[10px] uppercase font-bold text-rose-500 tracking-wider mt-1">
            Days
          </span>
        </div>

        {/* Hours */}
        <div className="bg-white rounded-xl py-2 px-1 shadow-sm border border-rose-100 flex flex-col items-center">
          <span className="font-serif-romantic text-2xl sm:text-3xl font-extrabold text-rose-950 leading-none">
            {pad(timeLeft.hours)}
          </span>
          <span className="text-[10px] uppercase font-bold text-rose-500 tracking-wider mt-1">
            Hours
          </span>
        </div>

        {/* Minutes */}
        <div className="bg-white rounded-xl py-2 px-1 shadow-sm border border-rose-100 flex flex-col items-center">
          <span className="font-serif-romantic text-2xl sm:text-3xl font-extrabold text-rose-950 leading-none">
            {pad(timeLeft.minutes)}
          </span>
          <span className="text-[10px] uppercase font-bold text-rose-500 tracking-wider mt-1">
            Mins
          </span>
        </div>

        {/* Seconds */}
        <div className="bg-rose-500/10 rounded-xl py-2 px-1 shadow-sm border border-rose-300 flex flex-col items-center">
          <span className="font-serif-romantic text-2xl sm:text-3xl font-extrabold text-rose-600 leading-none animate-pulse">
            {pad(timeLeft.seconds)}
          </span>
          <span className="text-[10px] uppercase font-bold text-rose-600 tracking-wider mt-1">
            Secs
          </span>
        </div>
      </div>

      {/* Anticipation caption */}
      <p className="text-[11px] text-rose-700/80 italic mt-2.5 text-center">
        ⏳ Counting every heartbeat until the clock strikes your special moment ✨
      </p>
    </div>
  );
};
