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

void printTree(HuffmanNode* root){

    if(root==NULL) return;

   
    cout<<root->letter<<" ";
    printTree(root->left);
    printTree(root->right);

}

pair<int,char> getChar(HuffmanNode* head,int i,string text){

    
	if(head->left==NULL && head->right==NULL){
		return {i,head->letter};
	}
	else{
		
		if(text[i]=='0'){

			return getChar(head->left,i+1 ,text);
		}
		else return getChar(head->right,i+1,text);
	}
}

void decode(HuffmanNode* root,string text,string &d_str){

	 int i=0;

	 while(i<text.length()){

	 		HuffmanNode* head=root;
            
            pair<int,char> ch=getChar(head,i,text);
            d_str.append(1,ch.second);
            i=ch.first;

	 }


}

HuffmanNode* generateTree(fstream &file,int &count){

    
	char ch;
	if(count==0) return NULL;
	file.get(ch);
	HuffmanNode* node=new HuffmanNode();

	if(ch=='1'){
		file.get(ch);
		node=new HuffmanNode();
		node->letter=ch;
		node->count=0;
		count--;
		node->left= node->right=NULL;
		return node; 
	}

	else{
		HuffmanNode* l=generateTree(file,count);
		HuffmanNode* r=generateTree(file,count);
		node=new HuffmanNode();
		node->letter='?';
		node->count=0;
		node->left= l;
		node->right=r;
        count--;
		return node; 
	}

	
}

int main(){
	
	fstream file;
	string filename,word;
	char c;

	string text="";
	filename="/home/komal/Documents/CN_Assignment2/output.txt";
	file.open(filename.c_str());

    int f_count=0;
    while(!file.eof()){

    	file.get(c);
    	if(c==',') break;
    	else {
    		f_count*=10;
    		f_count+=(int)c;
    	}
    }

    

    HuffmanNode* root=generateTree(file,f_count);

    printTree(root);

    while(!file.eof()){
    	file.get(c);
    	text.append(1,c);
    }

    string decodedStr="";
    cout<<endl<<text<<endl;
    decode(root,text,decodedStr);

    cout<<decodedStr;
    file.close();




}