#include <bits/stdc++.h>
using namespace std;

int main()
{
    int i, j, k, l;

    int fs;
    cout << "\n Enter Frame size: ";
    cin >> fs;

    int f[20];

    cout << "\n Enter Frame:";

    for (i = 0; i < fs; i++)
    {
        cin >> f[i];
    }

    int gs;
    cout << "\n Enter Generator size: ";
    cin >> gs;

    int g[20];

    cout << "\n Enter Generator:";

    for (i = 0; i < gs; i++)
    {
        cin >> g[i];
    }

    cout << "\n Sender Side:";

    cout << "\n Frame: ";
    for (i = 0; i < fs; i++)
    {
        cout << f[i];
    }
    cout << "\n Generator :";
    for (i = 0; i < gs; i++)
    {
        cout << g[i];
    }

    int rs = gs - 1;

    for (i = fs; i < fs + rs; i++)
    {
        f[i] = 0;
    }

    int temp[20];
    for (i = 0; i < 20; i++)
    {
        temp[i] = f[i];
    }

    cout << "\n Message after appending 0's :";
    for (i = 0; i < fs + rs; i++)
    {
        cout << temp[i];
    }

    for (i = 0; i < fs; i++)
    {
        j = 0;
        k = i;

        if (temp[k] >= g[j])
        {
            for (j = 0, k = i; j < gs; j++, k++)
            {
                if ((temp[k] == 1 && g[j] == 1) || (temp[k] == 0 && g[j] == 0))
                {
                    temp[k] = 0;
                }
                else
                {
                    temp[k] = 1;
                }
            }
        }
    }

    int crc[15];
    for (i = 0, j = fs; i < rs; i++, j++)
    {
        crc[i] = temp[j];
    }

    cout << "\n CRC bits: ";
    for (i = 0; i < rs; i++)
    {
        cout << crc[i];
    }

    cout << "\n Transmitted Frame: ";
    int tf[15];

    for (i = 0; i < fs; i++)
    {
        tf[i] = f[i];
    }
    for (i = fs, j = 0; i < fs + rs; i++, j++)
    {
        tf[i] = crc[j];
    }
    for (i = 0; i < fs + rs; i++)
    {
        cout << tf[i];
    }

    cout << "\n Receiver side : ";
    cout << "\n Received Frame: ";
    for (i = 0; i < fs + rs; i++)
    {
        cout << tf[i];
    }

    for (i = 0; i < fs + rs; i++)
    {
        temp[i] = tf[i];
    }

    for (i = 0; i < fs + rs; i++)
    {
        j = 0;
        k = i;
        if (temp[k] >= g[j])
        {
            for (j = 0, k = i; j < gs; j++, k++)
            {
                if ((temp[k] == 1 && g[j] == 1) || (temp[k] == 0 && g[j] == 0))
                {
                    temp[k] = 0;
                }
                else
                {
                    temp[k] = 1;
                }
            }
        }
    }

    cout << "\n Reminder: ";
    int rrem[15];

    for (i = fs, j = 0; i < fs + rs; i++, j++)
    {
        rrem[j] = temp[i];
    }

    for (i = 0; i < rs; i++)
    {
        cout << rrem[i];
    }

    int flag = 0;
    for (i = 0; i < rs; i++)
    {
        if (rrem[i] != 0)
        {
            flag = 1;
        }
    }

    if (flag == 0)
    {
        cout << "\n Since Remainder Is 0 Hence Message Transmitted From Sender To Receriver Is Correct";
    }
    else
    {
        cout << "\n Since Remainder Is Not 0 Hence Message Transmitted From Sender To Receriver Contains Error";
    }
}