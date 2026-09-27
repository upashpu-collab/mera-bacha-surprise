import React, { useEffect } from 'react';
import confetti from 'canvas-confetti';

interface ConfettiProps {
  continuous?: boolean;
}

export const triggerConfettiBurst = () => {
  const count = 200;
  const defaults = {
    origin: { y: 0.7 },
    colors: ['#E11D48', '#FF4081', '#FF80AB', '#FFB300', '#FFFFFF', '#FDA4AF']
  };

  function fire(particleRatio: number, opts: confetti.Options) {
    confetti({
      ...defaults,
      ...opts,
      particleCount: Math.floor(count * particleRatio)
    });
  }

  fire(0.25, {
    spread: 26,
    startVelocity: 55,
  });
  fire(0.2, {
    spread: 60,
  });
  fire(0.35, {
    spread: 100,
    decay: 0.91,
    scalar: 0.8
  });
  fire(0.1, {
    spread: 120,
    startVelocity: 25,
    decay: 0.92,
    scalar: 1.2
  });
  fire(0.1, {
    spread: 120,
    startVelocity: 45,
  });
};

export const ConfettiEffect: React.FC<ConfettiProps> = ({ continuous = false }) => {
  useEffect(() => {
    // Initial celebration blast
    triggerConfettiBurst();

    if (!continuous) return;

    // Gentle continuous rain of hearts & confetti
    const interval = window.setInterval(() => {
      confetti({
        particleCount: 8,
        angle: 60,
        spread: 55,
        origin: { x: 0, y: 0.6 },
        colors: ['#E11D48', '#FF4081', '#FFB300', '#FDA4AF']
      });
      confetti({
        particleCount: 8,
        angle: 120,
        spread: 55,
        origin: { x: 1, y: 0.6 },
        colors: ['#E11D48', '#FF4081', '#FFB300', '#FDA4AF']
      });
    }, 1200);

    return () => {
      window.clearInterval(interval);
    };
  }, [continuous]);

  return null;
};
