#include <iostream>
#include <cstdlib>
#include <time.h>

class Die
{
	private:
		int num_sides, die_value;
		static int num_dice;
	public:
		Die();
		int RollDie();
		void SetDieValue(int);
		int GetDieValue() const {return die_value;}
		void SetNumSides(int);
		int GetNumSides() const {return num_sides;}
		int GetNumDice() const {return num_dice;}
		~Die() {num_dice--;}
};

int Die::num_dice = 0;
Die::Die()
{
	SetNumSides(6);
	die_value = 1;
	num_dice++;
}
void Die::SetDieValue(int input)
{
	die_value = (input>0 && input<=num_sides) ? input : 1;
}
	
void Die::SetNumSides(int input)
{
	num_sides = (input < 2) ? 6 : input;
}

int Die::RollDie()
{
	//** rand() returns random number between 0 & max int inclusive **//
	die_value = (rand()%num_sides)+1;
	return die_value;
}


int main()
{
	srand(time(0));
	
	Die die;
	
	for (int i = 0; i < 10; i ++)
	{
		std::cout << "good tithings " << die.RollDie() << std::endl;
	}
	
	std::cin.get();
	
	return 0;
}