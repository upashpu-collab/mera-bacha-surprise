import React from 'react';

interface BalloonItem {
  id: number;
  left: number; // percentage 5% to 90%
  size: number;
  color: string;
  shineColor: string;
  delay: number;
  duration: number;
  stringLength: number;
}

export const Balloons: React.FC<{ count?: number }> = ({ count = 9 }) => {
  const balloons: BalloonItem[] = React.useMemo(() => {
    const palette = [
      { color: '#E11D48', shine: '#FDA4AF' }, // Crimson Rose
      { color: '#FF4081', shine: '#FF80AB' }, // Hot Pink
      { color: '#C084FC', shine: '#F3E8FF' }, // Soft Lavender
      { color: '#FB923C', shine: '#FED7AA' }, // Warm Peach
      { color: '#F472B6', shine: '#FCE7F3' }, // Pastel Pink
      { color: '#F87171', shine: '#FEE2E2' }, // Coral
    ];

    return Array.from({ length: count }, (_, i) => {
      const p = palette[i % palette.length];
      return {
        id: i,
        left: 6 + (i * 88) / count + (Math.random() * 6 - 3),
        size: 52 + Math.floor(Math.random() * 26),
        color: p.color,
        shineColor: p.shine,
        delay: Math.random() * 2.5,
        duration: 9 + Math.random() * 4,
        stringLength: 70 + Math.floor(Math.random() * 40)
      };
    });
  }, [count]);

  return (
    <div className="fixed inset-0 pointer-events-none overflow-hidden z-10">
      {balloons.map((b) => (
        <div
          key={b.id}
          className="absolute -bottom-36 flex flex-col items-center"
          style={{
            left: `${b.left}%`,
            animation: `balloonFloatUp ${b.duration}s linear infinite`,
            animationDelay: `${b.delay}s`,
          }}
        >
          {/* Balloon Body */}
          <div
            className="rounded-full shadow-lg relative flex items-center justify-center"
            style={{
              width: `${b.size}px`,
              height: `${b.size * 1.25}px`,
              background: `radial-gradient(circle at 35% 30%, ${b.shineColor}, ${b.color})`,
              boxShadow: `0 10px 25px -5px ${b.color}55`,
            }}
          >
            {/* White shine spot */}
            <div
              className="absolute top-2 left-3 rounded-full bg-white/50"
              style={{
                width: `${b.size * 0.25}px`,
                height: `${b.size * 0.4}px`,
                transform: 'rotate(-25deg)',
              }}
            />
          </div>

          {/* Balloon Knot */}
          <div
            className="w-2.5 h-2 -mt-0.5 rounded-b-sm"
            style={{ backgroundColor: b.color }}
          />

          {/* Balloon String */}
          <svg
            width="20"
            height={b.stringLength}
            className="opacity-60 -mt-0.5"
            style={{
              animation: 'swayString 3s ease-in-out infinite',
            }}
          >
            <path
              d={`M10,0 Q18,${b.stringLength * 0.3} 10,${b.stringLength * 0.6} T10,${b.stringLength}`}
              fill="none"
              stroke="#A57D88"
              strokeWidth="1.5"
            />
          </svg>
        </div>
      ))}

      <style>{`
        @keyframes balloonFloatUp {
          0% {
            transform: translateY(0) translateX(0);
            opacity: 0;
          }
          10% {
            opacity: 0.95;
          }
          50% {
            transform: translateY(-60vh) translateX(15px);
          }
          90% {
            opacity: 0.95;
          }
          100% {
            transform: translateY(-125vh) translateX(-15px);
            opacity: 0;
          }
        }
        @keyframes swayString {
          0%, 100% { transform: rotate(-3deg); }
          50% { transform: rotate(3deg); }
        }
      `}</style>
    </div>
  );
};
