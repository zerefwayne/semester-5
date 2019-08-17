fs=500e3; %Very high sampling rate 500 kHz
f=10e3; %Frequency of sinusoid
nCyl=5; %generate five cycles of sinusoid
t=0:1/fs:nCyl*1/f; %time index
x=cos(2*pi*f*t);

plot(t,x)
title('Continuous sinusoidal signal');
xlabel('Time(s)');
ylabel('Amplitude');
fs1=30e3; %30kHz sampling rate
t1=0:1/fs1:nCyl*1/f; %time index
x1=cos(2*pi*f*t1);

fs2=50e3; %50kHz sampling rate
t2=0:1/fs2:nCyl*1/f; %time index
x2=cos(2*pi*f*t2);

subplot(2,1,1);
plot(t,x);
hold on;
stem(t1,x1);
subplot(2,1,2);
plot(t,x);
hold on;
stem(t2,x2);