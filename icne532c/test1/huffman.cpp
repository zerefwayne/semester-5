#include<bits/stdc++.h>

using namespace std;

struct HuffmanNode{
	char letter;
	int count;
	HuffmanNode *left,*right;
};


struct myComparator{

	bool operator()(const HuffmanNode* a,const HuffmanNode* b){
		return (a->count)>(b->count);
	}
};

void printTree(HuffmanNode* root,map<char,string> &codes, string curr,map<char,int> freq,int &freqCount){

    if(root==NULL) return;

    if(freq.find(root->letter)!=freq.end())
    	codes[root->letter]+=curr;
    freqCount++;

    printTree(root->left,codes,curr+"0",freq,freqCount);
    printTree(root->right,codes,curr+"1",freq,freqCount);

}

void getTree(HuffmanNode* root,ofstream &file){

    // if(root==NULL) return;
	if(root->left==NULL && root->right == NULL){
		file << "1";
		file << root->letter;

	}

	else{
		file << "0";
		getTree(root->left,file);
		getTree(root->right,file);
	}

}

int main(){

	fstream file;
	string filename,word,filename2;
	char c;

	string text="";
	filename="/home/zerefwayne/semester-5/icne532c/test1/Q1.txt";
	file.open(filename.c_str());

	while(!file.eof()){
		file.get(c);
		text.append(1,c);
	}

    file.close();

	priority_queue < HuffmanNode*,vector<HuffmanNode*> , myComparator> table;


	map<char,int> charCount;

	for(char ch: text){
		charCount[ch]++;
	}

	for(auto x:charCount){
		HuffmanNode* node=new HuffmanNode();
		node->letter=x.first;
		node->count=x.second;
		node->left=NULL;
		node->right=NULL;
		table.push(node);
	}

	HuffmanNode* node=NULL;

	while(table.size()>=2){

		HuffmanNode* low1=table.top();
		table.pop();

		HuffmanNode* low2=table.top();
		table.pop();

		node=new HuffmanNode();

		node->letter='?';
		node->count=low1->count+low2->count;
		node->left=low1;
		node->right=low2;

		table.push(node);
	}

    HuffmanNode *root2,(*root)=new HuffmanNode();

    root=node;
    root2=node;

    map<char,string> storeCodes;

    int freqCount=0;

    printTree(root,storeCodes,"",charCount,freqCount);

    for(auto x:storeCodes){

    	cout<<x.first<<" "<<x.second<<endl;

    }

    string op="";

    string str="";

    ofstream file2;
    filename="/home/zerefwayne/semester-5/icne532c/test1/Q1out.txt";
    file2.open(filename.c_str());

    file2 << freqCount << ',';
    getTree(root2,file2);

    for(char ch:text){
    	file2 << storeCodes[ch];
    }

    file2.close();

}