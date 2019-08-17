clear all;

x=input('Please enter the sequence x(n)='); % e.g [1 2 3 4 5 6]
N=input('Please enter the length of the DFT N=');

X=fft(x,N);

n=0:length(x)-1;
subplot(3,1,1);

stem(n,x);

title('Input Sequence');
subplot(3,1,2);
n=0:length(X)-1;
stem(n,X);

disp('DFT of input sequence is ');
disp(X);
title('DFT');
idft=ifft(x,N);
subplot(3,1,3);
stem(n,abs(idft));
title('IDFT');
disp('IDFT of input sequence is ');
disp(idft);