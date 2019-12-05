#include <iostream>
#include <fstream>
#include <vector>
#include <time.h>
#include <stdlib.h>
using namespace std;

struct Node
{
    unsigned char character;
    Node *left;
    Node *right;
    Node(char c, Node *l = NULL, Node *r = NULL)
    {
        character = c;
        left = l;
        right = r;
    }
};

Node *Make_Huffman_tree(ifstream &input)
{
    char ch;
    input.get(ch);
    if (ch == '1')
    {
        input.get(ch);
        return (new Node(ch));
    }
    else
    {
        Node *left = Make_Huffman_tree(input);
        Node *right = Make_Huffman_tree(input);
        return (new Node(-1, left, right));
    }
}

void decode(ifstream &input, string filename, Node *Root, long long int Total_Freq)
{
    ofstream output((filename.erase(filename.size() - 4)).c_str(), ios::binary);
    if (!output.good())
    {
        perror("Error:\t");
        exit(-1);
    }
    bool eof_flag = false;
    char bits_8;
    Node *pointer = Root;
    while (input.get(bits_8))
    {
        int counter = 7;
        while (counter >= 0)
        {
            if (!pointer->left && !pointer->right)
            {
                output << pointer->character;
                Total_Freq--;
                if (!Total_Freq)
                {
                    eof_flag = true;
                    break;
                }
                pointer = Root;
                continue;
            }
            if ((bits_8 & (1 << counter)))
            {
                pointer = pointer->right;
                counter--;
            }
            else
            {
                pointer = pointer->left;
                counter--;
            }
        }
        if (eof_flag)
            break;
    }
    output.close();
}
int main()
{
    string filename, outfile;
    cout << "Enter the Filename:\t";
    cin >> filename;
    cout << "Enter the output filename:\t";
    cin >> outfile;
    ifstream input_file(filename.c_str(), ios::binary);
    if (!input_file.good())
    {
        perror("Error:\t");
        exit(-1);
    }
    if (filename.find(".huf") == string::npos)
    {
        cout << "Error: File is already decompressed\n\n";
        exit(-1);
    }
    cout << "\nDecompressing the file....";
    clock_t start_time = clock();
    long long int Total_freq = 0;
    char ch;
    while (input_file.get(ch))
    {
        if (ch == ',')
            break;
        Total_freq *= 10;
        Total_freq += ch - '0';
    }
    Node *Huffman_tree = Make_Huffman_tree(input_file);
    input_file.get(ch);
    decode(input_file, outfile, Huffman_tree, Total_freq);
    input_file.close();
    clock_t stop_time = clock();

    cout << "\n\n*******************************************File Decompressed Successfully! :-)*******************************************\n\n";
    cout << "Time taken to Compress:\t" << double(stop_time - start_time) / CLOCKS_PER_SEC << " seconds\n\n";
}
