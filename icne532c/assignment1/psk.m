clear all;
clc;
close all;

t=0:0.001:2;

fc=input('Carrier frequency: ');
fm=input('Input frequency: ');

A=1;
x=A.*sin(2*pi*fc*t);
u=A.*square(2*pi*fm*t);
v=x.*u;

subplot(3,1,1);
plot(t,x);
xlabel('Time');
ylabel('Amplitude');
title('Carrier Signal');

grid on;
subplot(3,1,2);
plot(t,u);
xlabel('Time');
ylabel('Amplitude');
title('Input Signal');

grid on;
subplot(3,1,3);
plot(t,v);
xlabel('Time');
ylabel('Amplitude');
title('PSK Signal');

grid on;
