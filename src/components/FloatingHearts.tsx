import React from 'react';

export const FloatingHearts: React.FC = () => {
  const hearts = React.useMemo(() => {
    return Array.from({ length: 18 }, (_, i) => ({
      id: i,
      left: Math.random() * 96,
      size: 14 + Math.random() * 16,
      duration: 10 + Math.random() * 8,
      delay: Math.random() * 6,
      opacity: 0.15 + Math.random() * 0.25,
      color: ['#F43F5E', '#FB7185', '#FDA4AF', '#E11D48', '#FFB3BA'][i % 5]
    }));
  }, []);

  return (
    <div className="fixed inset-0 pointer-events-none overflow-hidden z-0">
      {hearts.map((h) => (
        <div
          key={h.id}
          className="absolute -bottom-10"
          style={{
            left: `${h.left}%`,
            opacity: h.opacity,
            animation: `floatUpwards ${h.duration}s linear infinite`,
            animationDelay: `${h.delay}s`,
          }}
        >
          <svg
            width={h.size}
            height={h.size}
            viewBox="0 0 24 24"
            fill={h.color}
          >
            <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
          </svg>
        </div>
      ))}

      <style>{`
        @keyframes floatUpwards {
          0% {
            transform: translateY(0) rotate(0deg);
          }
          50% {
            transform: translateY(-55vh) rotate(15deg);
          }
          100% {
            transform: translateY(-110vh) rotate(-15deg);
          }
        }
      `}</style>
    </div>
  );
};
